package org.example.consumerportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "private_data_authorization")
@Data
public class PrivateDataAuthorizationEntity {

    @Id
    @Column(name = "private_data_access_id")
    @SequenceGenerator(name="identifier", sequenceName="private_data_authorization_private_data_access_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private Integer privateDataAccessId;

    @OneToOne
    @JoinColumn(name = "granted_producer_username", referencedColumnName = "username")
    private UserEntity grantedProducerUsername;

    @OneToOne
    @JoinColumn(name = "owning_producer_username", referencedColumnName = "username")
    private UserEntity owningProducerUsername;

    @Column(name = "manufacturer_data_field_name")
    private String manufacturerDataFieldName;

    @Column(name="record_start_date_time")
    private LocalDateTime startDateTime;

    @Column(name="record_end_date_time")
    private LocalDateTime endDateTime;

}
