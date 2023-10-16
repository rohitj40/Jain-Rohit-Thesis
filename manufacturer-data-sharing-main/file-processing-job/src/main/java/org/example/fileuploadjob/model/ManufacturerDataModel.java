package org.example.fileuploadjob.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ManufacturerDataModel {
    private String producerUsername;
    private String dataFieldName;
    private String dataFieldValue;
    private boolean isCommonData;
    private String sourceFile;
    private LocalDateTime uploadDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
