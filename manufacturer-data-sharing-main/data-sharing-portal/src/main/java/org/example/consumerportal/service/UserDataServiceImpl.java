package org.example.consumerportal.service;

import org.example.consumerportal.config.ApplicationConfigProperties;
import org.example.consumerportal.request.model.UserModel;
import org.example.consumerportal.response.model.UIDataModel;
import org.example.consumerportal.response.model.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDataServiceImpl {

    @Autowired
    private ManufacturerDataServiceImpl manufacturerDataService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MessageServieImpl messageService;

    @Autowired
    private ApplicationConfigProperties applicationConfigProperties;

    public UserDataModel getUserDataByProducerUsernme(String producerUsername) {
        UserDataModel dataModel = new UserDataModel();

        // Dashboard Data
        dataModel.setManufacturerCommonDataList(manufacturerDataService.getAllManufacturerCommonData(producerUsername));
        if(!dataModel.getManufacturerCommonDataList().isEmpty()) {
            dataModel.setManufacturerCommonDataColumnList(dataModel.getManufacturerCommonDataList().get(0));
            dataModel.getManufacturerCommonDataList().remove(0);
        } else {
            dataModel.setManufacturerCommonDataColumnList(Collections.emptyList());
        }

        // Private data
        dataModel.setManufacturerPrivateDataList(manufacturerDataService.getAllManufacturerPrivateDataByProducerName(producerUsername));
        if(!dataModel.getManufacturerPrivateDataList().isEmpty()) {
            dataModel.setManufacturerPrivateDataColumnList(dataModel.getManufacturerPrivateDataList().get(0));
            dataModel.getManufacturerPrivateDataList().remove(0);
        } else {
            dataModel.setManufacturerPrivateDataColumnList(Collections.emptyList());
        }

        // Granted Private data
        dataModel.setGrantedManufacturerPrivateDataList(manufacturerDataService.getAllGrantedManufacturerPrivateDataByProducerName(producerUsername));
        if(!dataModel.getGrantedManufacturerPrivateDataList().isEmpty()) {
            dataModel.setGrantedManufacturerPrivateDataColumnList(dataModel.getGrantedManufacturerPrivateDataList().get(0));
            dataModel.getGrantedManufacturerPrivateDataList().remove(0);
        } else {
            dataModel.setGrantedManufacturerPrivateDataColumnList(Collections.emptyList());
        }

        // Add granted private data into dasboard view
        if (applicationConfigProperties.getShowgranteddataindashboard()) {
            int noOfCols = dataModel.getManufacturerCommonDataColumnList().size();
            int noOfGrantedRows = dataModel.getGrantedManufacturerPrivateDataList().size();
            for (int row=0; row < noOfGrantedRows; row++) {
                List<UIDataModel> curDataRow = dataModel.getGrantedManufacturerPrivateDataList().get(row);
                if (dataModel.getManufacturerCommonDataList().size() >= row) {
                    dataModel.getManufacturerCommonDataList().get(row).addAll(curDataRow);
                } else {
                    List<UIDataModel> cols = new ArrayList<>();
                    for (int i=0; i<noOfCols; i++) {
                        UIDataModel col = new UIDataModel();
                        col.setId(-1);
                        col.setOwningProducerUsername(producerUsername);
                        col.setValue("-");
                        cols.add(col);
                    }
                    cols.addAll(curDataRow);
                    dataModel.getManufacturerCommonDataList().add(cols);
                }
            }
            dataModel.getManufacturerCommonDataColumnList().addAll(dataModel.getGrantedManufacturerPrivateDataColumnList());
        }

        // All sent access requests
        dataModel.setAllSentRequest(messageService.getAllActiveMessagesSentByProducer(producerUsername));

        // All received access requests
        dataModel.setAllReceivedRequest(messageService.getAllActiveMessagesReceivedForProducer(producerUsername));

        // All active manufacturers except self
        dataModel.setActiveManufacturers(customUserDetailsService.getAllActiveManufacturers().stream()
                        .filter(u -> !u.getUsername().equals(producerUsername)).collect(Collectors.toList()));

        return dataModel;
    }
}
