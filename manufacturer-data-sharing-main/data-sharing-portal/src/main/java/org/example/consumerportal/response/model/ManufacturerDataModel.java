package org.example.consumerportal.response.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class ManufacturerDataModel {
    private Integer manufacturerDataId;
    private String producerUsername;
    private String dataFieldName;
    private String dataFieldValue;
    private boolean isCommonData;
    private String sourceFile;
    private LocalDateTime uploadDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
