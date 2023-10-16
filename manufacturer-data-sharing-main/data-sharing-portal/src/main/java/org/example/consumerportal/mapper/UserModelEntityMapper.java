package org.example.consumerportal.mapper;

import org.example.consumerportal.entity.UserEntity;
import org.example.consumerportal.request.model.UserModel;
import org.mapstruct.Mapper;

@Mapper
public interface UserModelEntityMapper {
    UserModel entityToModel(UserEntity entity);
    UserEntity modelToEntity(UserModel model);
}
