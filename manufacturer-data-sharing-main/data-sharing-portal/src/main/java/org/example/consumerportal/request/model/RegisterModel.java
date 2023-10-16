package org.example.consumerportal.request.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class RegisterModel {
    private String userName;
    private String firstname;
    private String surname;
    private String email;

    private String tokenId;
    private String welcomeText;
    private String errorText;
}
