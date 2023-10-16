package org.example.consumerportal.repository;

import org.example.consumerportal.entity.ManufacturerDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerDataRepository extends JpaRepository<ManufacturerDataEntity, Integer> {
    List<ManufacturerDataEntity> findAllByProducerUsername(String producerUsername);
    List<ManufacturerDataEntity> findAllByIsCommonData(Boolean isCommonData);
    List<ManufacturerDataEntity> findAllByProducerUsernameAndDataFieldName(String producerUsername, String dataFieldName);
}
