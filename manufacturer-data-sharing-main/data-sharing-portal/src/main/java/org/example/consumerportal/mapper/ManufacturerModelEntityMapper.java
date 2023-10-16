package org.example.consumerportal.mapper;

import org.example.consumerportal.entity.ManufacturerDataEntity;
import org.example.consumerportal.response.model.ManufacturerDataModel;
import org.mapstruct.Mapper;

@Mapper
public interface ManufacturerModelEntityMapper {
    ManufacturerDataModel entityToModel(ManufacturerDataEntity entity);
    ManufacturerDataEntity modelToEntity(ManufacturerDataModel model);
}
