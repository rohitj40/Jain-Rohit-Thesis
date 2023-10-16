package org.example.consumerportal.response.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

@ToString
@Setter
@Getter
public class UserDataModel {
    private List<List<UIDataModel>> manufacturerCommonDataList;
    private List<UIDataModel> manufacturerCommonDataColumnList;

    private List<List<UIDataModel>> manufacturerPrivateDataList;
    private List<UIDataModel> manufacturerPrivateDataColumnList;

    private List<List<UIDataModel>> grantedManufacturerPrivateDataList;
    private List<UIDataModel> grantedManufacturerPrivateDataColumnList;

    private List<MessageModel> allSentRequest;
    private List<MessageModel> allReceivedRequest;

    private List<ProducerDataModel> activeManufacturers;

}
