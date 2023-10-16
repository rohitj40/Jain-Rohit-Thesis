package org.example.consumerportal.controller;

import org.example.consumerportal.request.model.TokenModel;
import org.example.consumerportal.service.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    private TokenServiceImpl tokenService;

    @PostMapping("/token")
    public ResponseEntity<?> getNewToken(@RequestBody TokenModel tokenModel, Errors errors) {
        tokenModel.setToken(tokenService.getNewToken(tokenModel.getEmail()));
        return ResponseEntity.ok(tokenModel);
    }
}
