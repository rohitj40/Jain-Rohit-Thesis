package org.example.consumerportal.service;

import org.example.consumerportal.entity.UserEntity;
import org.example.consumerportal.mapper.ProducerDataModelEntityMapper;
import org.example.consumerportal.mapper.UserModelEntityMapper;
import org.example.consumerportal.repository.UserRepository;
import org.example.consumerportal.request.model.UserModel;
import org.example.consumerportal.response.model.ProducerDataModel;
import org.example.consumerportal.response.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserModelEntityMapper userModelEntityMapper;

    @Autowired
    private ProducerDataModelEntityMapper producerDataModelEntityMapper;

    @Override
    public UserDetails loadUserByUsername(String tokenId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByPassword(tokenId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid token!"));

        UserDetails user = User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities("USER")
                .accountLocked(!userEntity.isAccountNonLocked()).build();
        return user;
    }

    public UserModel loadUserByEmail(String email) throws UsernameNotFoundException  {
        UserEntity userEntity = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email!"));

        return userModelEntityMapper.entityToModel(userEntity);
    }

    public List<ProducerDataModel> getAllActiveManufacturers() {
        List<UserEntity> entities = userRepository.findAllByEnabled(true);
        return entities.stream()
                .filter(e -> e.isAccountNonExpired() && e.isEnabled())
                .map(e -> producerDataModelEntityMapper.entityToModel(e))
                .collect(Collectors.toList());
    }

    public UserModel updateUserToken(UserModel user) {
        Optional<UserEntity> returnedEntity = userRepository.findById(user.getProducerId());

        if (returnedEntity.isPresent()) {
            UserEntity userEntity = returnedEntity.get();
            userEntity.setPassword(user.getPassword());
            userEntity.setTokenCreationDateTime(LocalDateTime.now());
            UserEntity savedEntity = userRepository.save(userEntity);

            return userModelEntityMapper.entityToModel(savedEntity);
        }
        return user;
    }
}