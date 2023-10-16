package org.example.fileuploadjob.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.example.fileuploadjob.adapter.FileProcessingAdapter;
import org.example.fileuploadjob.config.FileConfigProperties;
import org.example.fileuploadjob.entity.ManufacturerDataEntity;
import org.example.fileuploadjob.mapper.ManufacturerModelEntityMapper;
import org.example.fileuploadjob.model.FileModel;
import org.example.fileuploadjob.repository.ManufacturerDataRepository;
import org.example.fileuploadjob.util.FileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileProcessingService {
    private static final Logger log = LoggerFactory.getLogger(FileProcessingService.class);

    private Path rootLocation;
    private Path archiveLocation;
    private List<String> validFileExtensions;

    private Boolean saveToDb;
    private Boolean toarchive;

    @Autowired
    @Qualifier("CSVFileProcessingAdapter")
    private FileProcessingAdapter csvFileProcessingAdapter;

    @Autowired
    @Qualifier("ExcelFileProcessingAdapter")
    private FileProcessingAdapter excelFileProcessingAdapter;

    @Autowired
    @Qualifier("JsonFileProcessingAdapter")
    private FileProcessingAdapter jsonFileProcessingAdapter;

    @Autowired
    @Qualifier("XMLFileProcessingAdapter")
    private FileProcessingAdapter xmlFileProcessingAdapter;

    @Autowired
    private ManufacturerModelEntityMapper manufacturerModelEntityMapper;

    @Autowired
    private ManufacturerDataRepository manufacturerDataRepository;

    public FileProcessingService(FileConfigProperties fileConfigProperties) {
        this.validFileExtensions = fileConfigProperties.getExtensions();
        this.rootLocation = Paths.get(fileConfigProperties.getLocation());
        this.archiveLocation = Paths.get(fileConfigProperties.getArchivelocation());
        this.saveToDb = fileConfigProperties.getSavetodb();
        this.toarchive = fileConfigProperties.getToarchive();
    }

    public void processFiles() {
        log.info(" ######### Processing files are started ######### ");
        //Parse all the filePaths
        Set<String> filesToProcess = getAllPendingFiles();

        if (filesToProcess == null || filesToProcess.isEmpty()) {
            log.info("No files are found to process.");
        }

        //convert file contents to list of database entities
        Map<String, List<ManufacturerDataEntity>> fileEntitiesMap = getFileEntitiesMap(filesToProcess);

        //Save all the entities
        if (saveToDb){
            log.info("Saving entities to Database");
            saveData(fileEntitiesMap);
        }

        if (toarchive) {
            log.info("Archiving files");
            backupFiles(filesToProcess);
        }

        log.info(" ######### Processing files are finished ######### ");
    }

    private Map<String, List<ManufacturerDataEntity>> getFileEntitiesMap(Set<String> filesToProcess) {
        Map<String, List<ManufacturerDataEntity>> fileEntitiesMap = new HashMap<>();

        Map<String, Boolean> fieldNameToCommonMap = new HashMap<>();

        filesToProcess.stream().forEach(f -> {
            fileEntitiesMap.put(f, extractEntitiesFromFile(f, fieldNameToCommonMap));
        });

        //setting the common data attribute into the entity
        fileEntitiesMap.entrySet().stream().forEach(entry -> {
            entry.getValue().stream().forEach(entity -> {
                if (fieldNameToCommonMap.containsKey(entity.getDataFieldName())
                        && fieldNameToCommonMap.get(entity.getDataFieldName())) {
                    entity.setCommonData(true);
                }
            });
        });

        log.info("Models are converted into entities");
        return fileEntitiesMap;
    }

    private List<ManufacturerDataEntity> extractEntitiesFromFile(String filePath, Map<String, Boolean> fieldNameToCommonMap) {
        //Read a file and get the Model object for each row in the file
        FileModel fileModel = convertFileToDataModel(filePath);

        //resolve the field name which are synonyms, to compare them later for common data decision
        buildCommonFieldNames(fileModel.getFieldNames(), fieldNameToCommonMap);

        // Convert each model objects into database entity
        return fileModel.getModels().stream()
                .map(model -> manufacturerModelEntityMapper.modelToEntity(model)).collect(Collectors.toList());
    }

    private void buildCommonFieldNames(Set<String> fieldNames, Map<String, Boolean> fieldNameToCommonMap) {
        fieldNames.stream().forEach(f -> {
            if (fieldNameToCommonMap.containsKey(f)) {
                fieldNameToCommonMap.put(f, true);
            } else {
                fieldNameToCommonMap.put(f, false);
            }
        });
    }

    private FileModel convertFileToDataModel(String filePath) {
        log.info("Converting file " + filePath + " to datamodel.");
        if (filePath == null || filePath.length() == 0)
            return null;
        File processingFile = new File(filePath);
        String fileExtenstion = FilenameUtils.getExtension(processingFile.getName()).toUpperCase();
        if (FileExtension.XLSX.name().equals(fileExtenstion)) {
            return excelFileProcessingAdapter.getDataModelList(processingFile);
        } else if (FileExtension.CSV.name().equals(fileExtenstion)) {
            return csvFileProcessingAdapter.getDataModelList(processingFile);
        } else if (FileExtension.JSON.name().equals(fileExtenstion)) {
            return jsonFileProcessingAdapter.getDataModelList(processingFile);
        } else if (FileExtension.XML.name().equals(fileExtenstion)) {
            return xmlFileProcessingAdapter.getDataModelList(processingFile);
        }
        return null;
    }

    private Set<String> getAllPendingFiles() {
        List<String> directories = Stream.of(new File(this.rootLocation.toString()).listFiles())
                .filter(file -> file.isDirectory())
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        Set<String> files = new HashSet<>();
        for (String directory: directories) {
            files.addAll(Stream.of(new File(directory).listFiles())
                    .filter(file -> !file.isDirectory()
                            && validFileExtensions.contains(FilenameUtils.getExtension(file.getName())))
                    .map(File::getAbsolutePath)
                    .collect(Collectors.toList()));
        }

        log.info("List of files to process : " + files);
        return files;
    }

    private void saveData(Map<String, List<ManufacturerDataEntity>> fileEntitiesMap) {
        for (Map.Entry<String, List<ManufacturerDataEntity>> entry: fileEntitiesMap.entrySet()) {
            for (ManufacturerDataEntity entity: entry.getValue()) {
                try {
                    List<ManufacturerDataEntity> existingEntities = manufacturerDataRepository.findAllByProducerUsernameAndDataFieldName(entity.getProducerUsername(), entity.getDataFieldName());
                    if (existingEntities != null) {
                        existingEntities.forEach(e -> {
                            e.setEndDateTime(entity.getEndDateTime().plusSeconds(-1));
                            manufacturerDataRepository.save(e);
                        });
                    }
                    manufacturerDataRepository.save(entity);
                } catch (Exception e) {
                    log.error("Entity failed to be saved into DB " + e);
                }
            }

        }
    }

    private void backupFiles(Set<String> filePaths) {
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String dt = dtFormatter.format(LocalDateTime.now());

        File archiveDirectory = new File(archiveLocation.getParent().toString());
        if (!archiveDirectory.exists()) {
            archiveDirectory.mkdir();
        }

        filePaths.stream().forEach(filepath -> {

            String sourceFile = filepath.replace(rootLocation.toString(), "");
            String extenstion = FilenameUtils.getExtension(new File(filepath).getName());
            sourceFile = sourceFile.replace("." + extenstion, "_" + dt + "." + extenstion);
            Path destinationPath = Paths.get(archiveLocation.toString(), sourceFile);
            File desitnationFolder = new File(destinationPath.getParent().toString());
            if(!desitnationFolder.exists()) {
                desitnationFolder.mkdirs();
            }

            try {
                log.info("Archiving file " + filepath + " to " + destinationPath.toString());
                Path of = Path.of(filepath);
                Files.move(of, destinationPath);

                FileUtils.deleteDirectory(new File(of.getParent().toString()));
            } catch (IOException e) {
                log.error(filepath + "File not archived because : " + e);
            }
        });
    }
}
