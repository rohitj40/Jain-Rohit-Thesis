package org.example.fileuploadjob.adapter.impl;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.example.fileuploadjob.adapter.FileProcessingAdapter;
import org.example.fileuploadjob.model.FileModel;
import org.example.fileuploadjob.model.ManufacturerDataModel;
import org.example.fileuploadjob.util.DataFieldName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Qualifier("CSVFileProcessingAdapter")
public class CSVFileProcessingAdapter implements FileProcessingAdapter {

    private static final Logger log = LoggerFactory.getLogger(CSVFileProcessingAdapter.class);

    @Override
    public FileModel getDataModelList(File file) {
        LocalDateTime processingTime = LocalDateTime.now();
        List<ManufacturerDataModel> models = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>();

        Map<Integer, String> fieldPositions = new HashMap<>();
        String producerUserName = getProducerUsername(file);

        try {
            List<String[]> lines = readAllLines(file);

            //process only if lines has data other than the header row
            if(lines.size() > 1) {
                //collect headers and positions
                String[] headers = lines.get(0);
                int headerIdx = 0;
                for (String header: headers) {
                    fieldPositions.put(headerIdx, header);
                    headerIdx++;
                }
                //remove header row
                lines.remove(0);

                //iterate over all rows after header row
                lines.stream().forEach(colArr -> {

                    int colIdx = 0;
                    //consider the current row only if all columns are mentioned
                    if (colArr.length == fieldPositions.keySet().size()) {
                        // iterate all columns of the current row
                        for (String col: colArr) {
                            if("Producer ID".equals(fieldPositions.get(colIdx))) {
                                colIdx++;
                                continue;
                            } else {
                                if(StringUtils.isNotBlank(col)) {
                                    ManufacturerDataModel model = new ManufacturerDataModel();
                                    model.setProducerUsername(producerUserName);
                                    model.setCommonData(false);
                                    model.setSourceFile(file.getName());
                                    model.setDataFieldName(fieldPositions.get(colIdx).trim());
                                    model.setDataFieldValue(col.trim());
                                    model.setStartDateTime(processingTime);
                                    model.setEndDateTime(processingTime.plusYears(300));
                                    model.setUploadDateTime(processingTime);
                                    for (DataFieldName fieldNameEnum: DataFieldName.values()) {
                                        if(fieldNameEnum.getSynonyms().stream()
                                                .anyMatch(s -> s.replace("//s", "").toUpperCase()
                                                        .equals(model.getDataFieldName().replace("//s", "").toUpperCase()))) {
                                            model.setDataFieldName(fieldNameEnum.getDataFieldName());
                                        }
                                    }
                                    models.add(model);
                                }
                            }
                            colIdx++;
                        }
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        models.stream().forEach(m -> fieldNames.add(m.getDataFieldName()));

        FileModel output = new FileModel();
        output.setFieldNames(fieldNames);
        output.setModels(models);

        return output;
    }

    private List<String[]> readAllLines(File file) throws Exception {
        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }

}
