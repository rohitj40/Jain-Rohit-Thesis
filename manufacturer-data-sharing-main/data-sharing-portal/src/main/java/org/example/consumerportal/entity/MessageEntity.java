package org.example.consumerportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class MessageEntity {

    @Id
    @Column(name = "message_id")
    @SequenceGenerator(name="identifier", sequenceName="message_message_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private Integer messageId;

    @Column(name="asked_by_producer_username")
    private String askedByProducerUsername;

    @Column(name="asked_to_producer_username")
    private String askedToProducerUsername;

    @Column(name="message_body")
    private String messageBody;

    @Column(name="message_subject")
    private String messageSubject;

    @Column(name="message_reply")
    private String messageReply;

    @Column(name="message_sent_date_time")
    private LocalDateTime messageSentDateTime;

    @Column(name="reply_sent_date_time")
    private LocalDateTime replySentDateTime;

    @Column(name="is_message_sent")
    private Boolean isMessageSent;

    @Column(name="is_message_replied")
    private Boolean isMessageReplied;
}
