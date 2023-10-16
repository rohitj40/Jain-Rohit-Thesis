package org.example.consumerportal.service;

import org.apache.commons.lang3.StringUtils;
import org.example.consumerportal.entity.ManufacturerDataEntity;
import org.example.consumerportal.entity.PrivateDataAuthorizationEntity;
import org.example.consumerportal.entity.UserEntity;
import org.example.consumerportal.repository.ManufacturerDataRepository;
import org.example.consumerportal.repository.PrivateDataAuthorizationRepository;
import org.example.consumerportal.repository.UserRepository;
import org.example.consumerportal.request.model.PrivateDataAccessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrivateDataAuthorizationServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManufacturerDataRepository manufacturerDataRepository;

    @Autowired
    private PrivateDataAuthorizationRepository privateDataAuthorizationRepository;

    public String savePrivateDataAuthorization(PrivateDataAccessModel accessModel) {
        try {
            List<String> dataFieldNames = new ArrayList<>();
            if (StringUtils.isNotBlank(accessModel.getDataFieldNames())) {
                for (String data : accessModel.getDataFieldNames().trim().split(",")) {
                    dataFieldNames.add(data.trim());
                }
            }

            Optional<UserEntity> owningUserEntity = userRepository.findUserByUsername(accessModel.getOwningProducerUsername());
            Optional<UserEntity> grantedUserEntity = userRepository.findUserByEmail(accessModel.getGrantedEmail());

            List<String> listOfAlreadyFoundDataAccess = new ArrayList<>();
            if (owningUserEntity.isPresent() && grantedUserEntity.isPresent()) {
                //if user is trying to give access to himself
                if(accessModel.getOwningProducerUsername().equals(grantedUserEntity.get().getUsername())) {
                    return "ownPermission";
                }

                List<PrivateDataAuthorizationEntity> existingAccess = new ArrayList<>();
                dataFieldNames.forEach(d -> {
                    existingAccess.addAll(privateDataAuthorizationRepository.findAllByGrantedProducerUsernameAndOwningProducerUsernameAndManufacturerDataFieldName(grantedUserEntity.get(), owningUserEntity.get(), d));
                });
                List<PrivateDataAuthorizationEntity> existingActiveAccess = existingAccess.stream().filter(p -> p.getEndDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());

                // active access is found for all the given dataFields
                if (existingActiveAccess.size() == dataFieldNames.size()) {
                    return "alreadyFound";
                }

                //remove all datafieldnames for which active access is already found
                existingActiveAccess.stream().forEach(p -> dataFieldNames.remove(p.getManufacturerDataFieldName()));

                //for each remaining datafields add access for the granted user
                dataFieldNames.stream().forEach(d -> {
                    PrivateDataAuthorizationEntity toBeSavedEntity = new PrivateDataAuthorizationEntity();
                    toBeSavedEntity.setManufacturerDataFieldName(d);
                    toBeSavedEntity.setOwningProducerUsername(owningUserEntity.get());
                    toBeSavedEntity.setGrantedProducerUsername(grantedUserEntity.get());
                    toBeSavedEntity.setStartDateTime(LocalDateTime.now());
                    toBeSavedEntity.setEndDateTime(LocalDateTime.now().plusHours(24));

                    privateDataAuthorizationRepository.save(toBeSavedEntity);
                });
                return "success";
            }
            return "sentUserNotFound";

        } catch (Exception e) {
            return "fail";
        }
    }

    public String removePrivateDataAuthorization(PrivateDataAccessModel accessModel) {
        try {
            List<String> dataFieldNames = new ArrayList<>();
            if (StringUtils.isNotBlank(accessModel.getDataFieldNames())) {
                for (String data : accessModel.getDataFieldNames().trim().split(",")) {
                    dataFieldNames.add(data.trim());
                }
            }

            Optional<UserEntity> owningUserEntity = userRepository.findUserByUsername(accessModel.getOwningProducerUsername());
            Optional<UserEntity> grantedUserEntity = userRepository.findUserByEmail(accessModel.getGrantedEmail());

            if (owningUserEntity.isPresent() && grantedUserEntity.isPresent()) {

                //if user is trying to remove access to himself
                if(accessModel.getOwningProducerUsername().equals(grantedUserEntity.get().getUsername())) {
                    return "ownPermission";
                }

                List<PrivateDataAuthorizationEntity> existingAccess = new ArrayList<>();
                dataFieldNames.forEach(d -> {
                    existingAccess.addAll(privateDataAuthorizationRepository.findAllByGrantedProducerUsernameAndOwningProducerUsernameAndManufacturerDataFieldName(grantedUserEntity.get(), owningUserEntity.get(), d));
                });
                List<PrivateDataAuthorizationEntity> existingActiveAccess = existingAccess.stream().filter(p -> p.getEndDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());

                // no access is found for any dataFields
                if (existingActiveAccess == null || existingActiveAccess.isEmpty()) {
                    return "notFound";
                }

                //for each remaining datafields add access for the granted user
                existingActiveAccess.stream().forEach(p -> {
                    p.setEndDateTime(LocalDateTime.now());
                    privateDataAuthorizationRepository.save(p);
                });

                return "success";
            }
            return "removedUserNotFound";
        } catch (Exception e) {
            return "fail";
        }
    }


    public List<ManufacturerDataEntity> getAllGrantedPrivateData(String grantedProducerName) {
        Optional<UserEntity> userEntity = userRepository.findUserByUsername(grantedProducerName);
        List<ManufacturerDataEntity> output = new ArrayList<>();
        if(userEntity.isPresent()) {
            List<PrivateDataAuthorizationEntity> pvtDataEntities =
                    privateDataAuthorizationRepository.findAllByGrantedProducerUsername(userEntity.get());
            List<String> ownerProdcuerAndDataFieldNames =  pvtDataEntities.stream()
                    .filter(p-> p.getEndDateTime().isAfter(LocalDateTime.now()))
                    .map(p -> p.getOwningProducerUsername().getUsername() + "###" + p.getManufacturerDataFieldName())
                    .toList();
            ownerProdcuerAndDataFieldNames.forEach(d -> {
                output.addAll(manufacturerDataRepository.findAllByProducerUsernameAndDataFieldName(d.split("###")[0], d.split("###")[1]));
            });
        }
        return output;
    }

}
