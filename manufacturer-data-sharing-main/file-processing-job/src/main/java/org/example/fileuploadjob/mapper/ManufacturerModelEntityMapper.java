package org.example.fileuploadjob.mapper;

import org.example.fileuploadjob.entity.ManufacturerDataEntity;
import org.example.fileuploadjob.model.ManufacturerDataModel;
import org.mapstruct.Mapper;

@Mapper
public interface ManufacturerModelEntityMapper {
    ManufacturerDataModel entityToModel(ManufacturerDataEntity entity);
    ManufacturerDataEntity modelToEntity(ManufacturerDataModel model);
}
