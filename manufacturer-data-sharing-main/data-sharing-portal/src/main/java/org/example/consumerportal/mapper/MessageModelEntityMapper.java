package org.example.consumerportal.mapper;

import org.example.consumerportal.entity.MessageEntity;
import org.example.consumerportal.response.model.MessageModel;
import org.mapstruct.Mapper;

@Mapper
public interface MessageModelEntityMapper {
    MessageEntity modelToEntity(MessageModel model);
    MessageModel entityToModel(MessageEntity entity);
}
