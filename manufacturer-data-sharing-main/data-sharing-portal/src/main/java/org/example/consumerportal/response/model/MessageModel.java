package org.example.consumerportal.response.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageModel {
    private Integer messageId;
    private String askedByProducerUsername;
    private String askedByProducerUIDetails;
    private String askedToProducerUsername;
    private String askedToProducerUIDetails;
    private String messageBody;
    private String messageSubject;
    private String messageReply;
    private LocalDateTime messageSentDateTime;
    private LocalDateTime replySentDateTime;
    private Boolean isMessageSent;
    private Boolean isMessageReplied;
    private String apiResponse;
}
