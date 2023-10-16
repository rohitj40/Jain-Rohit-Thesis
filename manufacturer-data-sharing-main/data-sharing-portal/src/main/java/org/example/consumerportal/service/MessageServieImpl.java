package org.example.consumerportal.service;

import org.example.consumerportal.entity.MessageEntity;
import org.example.consumerportal.entity.UserEntity;
import org.example.consumerportal.mapper.MessageModelEntityMapper;
import org.example.consumerportal.repository.MessageRepository;
import org.example.consumerportal.repository.UserRepository;
import org.example.consumerportal.response.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServieImpl {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageModelEntityMapper mapper;

    public List<MessageModel> getAllActiveMessagesSentByProducer(String producerUsername) {
        List<MessageEntity> allMessages = messageRepository.findAllByAskedByProducerUsername(producerUsername);
        Optional<UserEntity> askedByUserEntity = userRepository.findUserByUsername(producerUsername);

        if (allMessages != null) {
            return allMessages.stream().map(e -> {
                MessageModel model = mapper.entityToModel(e);
                Optional<UserEntity> askedToUserEntity = userRepository.findUserByUsername(model.getAskedToProducerUsername());
                if (askedToUserEntity.isPresent()) {
                    UserEntity entity = askedToUserEntity.get();
                    model.setAskedToProducerUIDetails(entity.getFirstName() + " " + entity.getSurname() + " (" + entity.getEmail() + ")");
                }
                if (askedByUserEntity.isPresent()) {
                    UserEntity entity = askedByUserEntity.get();
                    model.setAskedByProducerUIDetails(entity.getFirstName() + " " + entity.getSurname() + " (" + entity.getEmail() + ")");
                }
                return model;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<MessageModel> getAllActiveMessagesReceivedForProducer(String producerUsername) {
        List<MessageEntity> allMessages = messageRepository.findAllByAskedToProducerUsername(producerUsername);
        Optional<UserEntity> askedToUserEntity = userRepository.findUserByUsername(producerUsername);
        if (allMessages != null) {
            return allMessages.stream().map(e -> {
                MessageModel model = mapper.entityToModel(e);
                Optional<UserEntity> askedByUserEntity = userRepository.findUserByUsername(model.getAskedByProducerUsername());
                if (askedByUserEntity.isPresent()) {
                    UserEntity entity = askedByUserEntity.get();
                    model.setAskedByProducerUIDetails(entity.getFirstName() + " " + entity.getSurname() + " (" + entity.getEmail() + ")");
                }
                if (askedToUserEntity.isPresent()) {
                    UserEntity entity = askedToUserEntity.get();
                    model.setAskedToProducerUIDetails(entity.getFirstName() + " " + entity.getSurname() + " (" + entity.getEmail() + ")");
                }
                return model;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public String saveMessage(MessageModel model) {
        try {
            LocalDateTime now = LocalDateTime.now();
            MessageEntity entity = mapper.modelToEntity(model);
            entity.setIsMessageSent(true);
            entity.setIsMessageReplied(false);
            entity.setMessageSentDateTime(now);
            entity.setReplySentDateTime(null);
            messageRepository.save(entity);
        } catch (Exception e) {
            return "technicalError";
        }
        return "success";
    }

    public String saveReply(MessageModel model) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Optional<MessageEntity> optional = messageRepository.findById(model.getMessageId());
            MessageEntity entity = null;
            if (optional.isPresent()) {
                entity = optional.get();
                entity.setIsMessageReplied(true);
                entity.setReplySentDateTime(now);
                entity.setMessageReply(model.getMessageReply());
                messageRepository.save(entity);
                return "success";
            } else {
                return "notFound";
            }
        } catch (Exception e) {
            return "technicalError";
        }
    }
}
