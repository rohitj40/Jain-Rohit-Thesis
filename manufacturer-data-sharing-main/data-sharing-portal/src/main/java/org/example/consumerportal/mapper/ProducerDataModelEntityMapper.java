package org.example.consumerportal.mapper;

import org.example.consumerportal.entity.UserEntity;
import org.example.consumerportal.response.model.ProducerDataModel;
import org.mapstruct.Mapper;

@Mapper
public interface ProducerDataModelEntityMapper {
    ProducerDataModel entityToModel(UserEntity entity);
    UserEntity modelToEntity(ProducerDataModel model);
}
