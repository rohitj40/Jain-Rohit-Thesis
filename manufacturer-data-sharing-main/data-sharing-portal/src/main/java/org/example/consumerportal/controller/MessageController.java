package org.example.consumerportal.controller;

import org.example.consumerportal.response.model.MessageModel;
import org.example.consumerportal.service.MessageServieImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageServieImpl messageServie;

    @PostMapping("/message")
    public ResponseEntity<?> saveMessage(@RequestBody MessageModel model, Errors errors) {
        logger.info("Received message save request");
        model.setApiResponse(messageServie.saveMessage(model));
        return ResponseEntity.ok(model);
    }

    @PostMapping("/reply")
    public ResponseEntity<?> saveReply(@RequestBody MessageModel model, Errors errors) {
        logger.info("Received reply save request");
        model.setApiResponse(messageServie.saveReply(model));
        return ResponseEntity.ok(model);
    }
}
