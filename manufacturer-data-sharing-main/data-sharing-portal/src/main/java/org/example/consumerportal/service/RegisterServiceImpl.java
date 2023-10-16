package org.example.consumerportal.service;

import org.example.consumerportal.entity.UserEntity;
import org.example.consumerportal.repository.UserRepository;
import org.example.consumerportal.request.model.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterModel saveRegister(RegisterModel registerModel) {

        Optional<UserEntity> existingUser = userRepository.findUserByEmail(registerModel.getEmail());

        if (existingUser.isPresent()) {
            registerModel.setErrorText(registerModel.getEmail() + " is already registered.");
            registerModel.setWelcomeText(null);
            return registerModel;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerModel.getFirstname());
        userEntity.setSurname(registerModel.getSurname());
        userEntity.setPassword(registerModel.getTokenId());
        userEntity.setEmail(registerModel.getEmail());
        userEntity.setEnabled(true);
        userEntity.setAccountNonExpired(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity = userRepository.save(userEntity);

        Optional<UserEntity> lodedUser = userRepository.findById(userEntity.getProducerId());
        if (lodedUser.isPresent()) {
            userEntity = lodedUser.get();
            userEntity.setUsername("PD" + userEntity.getProducerId());
            userEntity = userRepository.save(userEntity);
            registerModel.setUserName(userEntity.getUsername());
            registerModel.setWelcomeText("Welcome " +  registerModel.getFirstname()
                    + " ! Your user id is " + registerModel.getUserName() + " and token is " + registerModel.getTokenId());
            registerModel.setErrorText(null);
        }

        return registerModel;
    }

}
