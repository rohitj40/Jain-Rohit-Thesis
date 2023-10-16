package org.example.consumerportal.controller;

import org.example.consumerportal.request.model.PrivateDataAccessModel;
import org.example.consumerportal.service.PrivateDataAuthorizationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateDataAuthorizationController {

    private static Logger logger = LoggerFactory.getLogger(PrivateDataAuthorizationController.class);

    @Autowired
    private PrivateDataAuthorizationServiceImpl privateDataAuthorizationService;

    @PostMapping("/privatedataaccessgrant")
    private ResponseEntity<?> grantPrivateDataAuthorization(@RequestBody PrivateDataAccessModel dataAccessModel, Errors errors) {
        logger.info("Received request for grantPrivateDataAuthorization");
        dataAccessModel.setResponse(privateDataAuthorizationService
                .savePrivateDataAuthorization(dataAccessModel));
        return ResponseEntity.ok(dataAccessModel);
    }

    @PostMapping("/privatedataaccessremove")
    private ResponseEntity<?> removePrivateDataAuthorization(@RequestBody PrivateDataAccessModel dataAccessModel, Errors errors) {
        logger.info("Received request for removePrivateDataAuthorization");
        dataAccessModel.setResponse(privateDataAuthorizationService
                .removePrivateDataAuthorization(dataAccessModel));
        return ResponseEntity.ok(dataAccessModel);
    }
}
