package org.example.consumerportal.request.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class TokenModel {
    private String email;
    private String token;
}
