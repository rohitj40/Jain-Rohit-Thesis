package org.example.consumerportal.service;

import org.apache.commons.io.FilenameUtils;
import org.example.consumerportal.config.FileConfigProperties;
import org.example.consumerportal.exception.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileUploadServiceImpl {

    private Path rootLocation;
    private List<String> validFileExtensions;
    public FileUploadServiceImpl(FileConfigProperties fileConfigProperties) {
        this.validFileExtensions = fileConfigProperties.getExtensions();
        this.rootLocation = Paths.get(fileConfigProperties.getLocation());
    }

    public String storeFile(MultipartFile file, String producerUserName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Empty file provided.");
            }

            if (!validFileExtensions.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
                return "invalidFile";
            }

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(producerUserName, file.getOriginalFilename()))
                                        .normalize().toAbsolutePath();
            if (!destinationFile.getParent().toString().startsWith(this.rootLocation.toAbsolutePath().toString())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            File directory = new File(destinationFile.getParent().toString());
            if (!directory.exists()) {
                directory.mkdir();
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                return "success";
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

}
