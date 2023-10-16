package org.example.consumerportal.service;

import org.example.consumerportal.request.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public String getNewToken(String email) {
        UserModel user = customUserDetailsService.loadUserByEmail(email);
        if(user != null) {
            String tempToken = user.getFirstName() + user.getSurname() + user.getEmail() + LocalDateTime.now();
            user.setPassword(user.getFirstName().toUpperCase().substring(0, 2)
                    + Math.abs(tempToken.toUpperCase().hashCode()));
            user = customUserDetailsService.updateUserToken(user);
            return user.getPassword();
        }
        return "";
    }

}
