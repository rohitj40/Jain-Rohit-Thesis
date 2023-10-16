package org.example.consumerportal.service;

import org.example.consumerportal.entity.ManufacturerDataEntity;
import org.example.consumerportal.mapper.ManufacturerModelEntityMapper;
import org.example.consumerportal.repository.ManufacturerDataRepository;
import org.example.consumerportal.response.model.UIDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManufacturerDataServiceImpl {

    public static final String DELIMITER_STRING = "###";
    public static final String OWNING_PRODUCER = "Owning Producer";
    @Autowired
    private ManufacturerDataRepository manufacturerDataRepository;

    @Autowired
    private PrivateDataAuthorizationServiceImpl privateDataAuthorizationService;

    @Autowired
    private ManufacturerModelEntityMapper manufacturerModelEntityMapper;

    public List<List<UIDataModel>> getAllManufacturerPrivateDataByProducerName(String producerUsername) {
        List<ManufacturerDataEntity> manufacturerList = manufacturerDataRepository.findAllByProducerUsername(producerUsername);
        return getDataTable(manufacturerList.stream()
                .filter(m -> !m.isCommonData() && m.getEndDateTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList()), false, producerUsername);
    }

    public List<List<UIDataModel>> getAllGrantedManufacturerPrivateDataByProducerName(String producerUsername) {
        List<ManufacturerDataEntity> manufacturerList = privateDataAuthorizationService.getAllGrantedPrivateData(producerUsername);
        return getDataTable(manufacturerList.stream()
                .filter(m -> !m.isCommonData() && m.getEndDateTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList()), false, producerUsername);
    }


    public List<List<UIDataModel>> getAllManufacturerCommonData(String producerUsername) {
        List<ManufacturerDataEntity> manufacturerList = manufacturerDataRepository.findAllByIsCommonData(true);
        return getDataTable(manufacturerList.stream()
                .filter(m -> m.getEndDateTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList()), true, producerUsername);
    }

    private List<List<UIDataModel>> getDataTable(List<ManufacturerDataEntity> manufacturerList, boolean isForCommonData, String producerUsername) {
        Map<String, List<String>> result = new HashMap<>();
        manufacturerList.stream().forEach(m -> {
            List<String> dataList = result.getOrDefault(m.getDataFieldName(), new ArrayList<>());
            dataList.add(m.getManufacturerDataId() + DELIMITER_STRING + m.getDataFieldValue());
            result.put(m.getDataFieldName(), dataList);

            List<String> ownerProducerList = result.getOrDefault(OWNING_PRODUCER, new ArrayList<>());
            ownerProducerList.add(m.getManufacturerDataId() + DELIMITER_STRING + m.getProducerUsername());
            result.put(OWNING_PRODUCER, ownerProducerList);
        });



        List<List<UIDataModel>> output = new ArrayList<>();
        if (result.isEmpty()) {
            return output;
        }

        List<String> owningProducerUsernames = result.get(OWNING_PRODUCER);
        Map<String, String> dataIdToOwningProducerIdMap = new HashMap<>();
        // Do not need to show Owning Producer in UI for Private Data
        if (!isForCommonData) {
            result.remove(OWNING_PRODUCER);
        } else {  // build a map of  owning producerNames against dataId for common Data UI view

            for (String owningProducerName: owningProducerUsernames) {
                String dataId = owningProducerName.split(DELIMITER_STRING)[0];
                String owner = owningProducerName.split(DELIMITER_STRING)[1];
                dataIdToOwningProducerIdMap.put(dataId, owner);
            }
        }

        if (result != null && !result.isEmpty()
                && !result.get(result.entrySet().stream().findFirst().get().getKey()).isEmpty()) {
            List<UIDataModel> header = new ArrayList<>();

            header.addAll(result.keySet().stream()
                    .filter(k -> !k.equals(OWNING_PRODUCER))
                    .map(k -> {
                        UIDataModel model = new UIDataModel();
                        model.setId(-1);
                        model.setValue(k);
                        model.setOwningProducerUsername(producerUsername);
                        return model;
            }).collect(Collectors.toList()));
            output.add(header);

            int numOfRows = 0;
            for (Map.Entry<String, List<String>> entry: result.entrySet()) {
                if(!OWNING_PRODUCER.equals(entry.getKey())
                        && numOfRows < entry.getValue().size()) {
                    numOfRows = entry.getValue().size();
                }
            }

            for (int idx = 0; idx < numOfRows; idx++) {
                List<UIDataModel> row = new ArrayList<>();
                String owningProducerOfRow = null;
                for (UIDataModel col: header) {
                    if (isForCommonData && OWNING_PRODUCER.equals(col.getValue())) {
                        continue;
                    }
                    UIDataModel model = new UIDataModel();
                    try {
                        if (result.get(col.getValue()) != null && result.get(col.getValue()).size() >= idx) {
                            String val = result.get(col.getValue()).get(idx);

                            model.setId(Integer.parseInt(val.split(DELIMITER_STRING)[0]));
                            model.setValue(val.split(DELIMITER_STRING)[1]);
                            if (owningProducerOfRow == null) {
                                owningProducerOfRow = dataIdToOwningProducerIdMap.get(model.getId().toString());
                            }
                            model.setOwningProducerUsername(owningProducerOfRow);

                            if (!isForCommonData) {
                                Optional<String> owningProd = owningProducerUsernames.stream().filter(p -> p.split(DELIMITER_STRING)[0].equals(val.split(DELIMITER_STRING)[0])).findFirst();
                                if (owningProd.isPresent() && !owningProd.get().split(DELIMITER_STRING)[1].equals(producerUsername)) {
                                    model.setValue(val.split(DELIMITER_STRING)[1]);
                                    model.setOwningProducerUsername(owningProd.get().split(DELIMITER_STRING)[1]);
                                } else {
                                    model.setOwningProducerUsername(producerUsername);
                                }
                            }
                        } else {
                            model.setId(-1);
                            model.setValue("-");
                            model.setOwningProducerUsername("-");
                        }
                    } catch (Exception e) {
                        model.setId(-1);
                        model.setValue("-");
                        model.setOwningProducerUsername("-");
                    }
                    row.add(model);
                }
                output.add(row);
            }
        }

        return output;
    }
}
