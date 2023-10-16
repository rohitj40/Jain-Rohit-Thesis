package org.example.consumerportal.repository;

import org.example.consumerportal.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByAskedByProducerUsername(String askedByProducerUsername);
    List<MessageEntity> findAllByAskedToProducerUsername(String askedToProducerUsername);
}
