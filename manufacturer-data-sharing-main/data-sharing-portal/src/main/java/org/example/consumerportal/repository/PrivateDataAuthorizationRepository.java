package org.example.consumerportal.repository;

import org.example.consumerportal.entity.ManufacturerDataEntity;
import org.example.consumerportal.entity.PrivateDataAuthorizationEntity;
import org.example.consumerportal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivateDataAuthorizationRepository extends JpaRepository<PrivateDataAuthorizationEntity, UserEntity> {
    List<PrivateDataAuthorizationEntity> findAllByGrantedProducerUsername(UserEntity grantedProducerUsername);
    List<PrivateDataAuthorizationEntity> findAllByGrantedProducerUsernameAndOwningProducerUsernameAndManufacturerDataFieldName(UserEntity grantedProducerUsername, UserEntity owningProducerUsername, String manufacturerDataFieldName);
}
