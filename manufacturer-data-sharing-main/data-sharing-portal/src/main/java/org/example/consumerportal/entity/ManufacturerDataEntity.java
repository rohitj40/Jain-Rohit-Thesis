package org.example.consumerportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "manufacturer_data")
@Data
public class ManufacturerDataEntity {

    @Id
    @Column(name = "manufacturer_data_id")
    private Integer manufacturerDataId;

    @Column(name = "producer_username")
    private String producerUsername;

    @Column(name = "data_field_name")
    private String dataFieldName;

    @Column(name = "data_field_value")
    private String dataFieldValue;

    @Column(name = "is_common_data")
    private boolean isCommonData;

    @Column(name = "source_file")
    private String sourceFile;

    @Column(name = "upload_date_time")
    private LocalDateTime uploadDateTime;

    @Column(name = "record_start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "record_end_date_time")
    private LocalDateTime endDateTime;

}
