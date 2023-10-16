package org.example.consumerportal.request.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
public class UserModel {
    private Integer producerId;
    private String username;
    private String firstName;
    private String surname;
    private String email;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private String tabNameToDisplayRequestReceived;
    private String tabNameToDisplayRequestSent;
}
