package org.example.fileuploadjob.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "manufacturer_data")
@Data
public class ManufacturerDataEntity {

    @Id
    @Column(name = "manufacturer_data_id")
    @SequenceGenerator(name="identifier", sequenceName="manufacturer_data_manufacturer_data_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManufacturerDataEntity that = (ManufacturerDataEntity) o;
        return Objects.equals(dataFieldName, that.dataFieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataFieldName);
    }
}
