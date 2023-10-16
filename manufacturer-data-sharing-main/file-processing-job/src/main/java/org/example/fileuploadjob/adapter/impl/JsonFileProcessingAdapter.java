package org.example.fileuploadjob.adapter.impl;

import org.example.fileuploadjob.adapter.FileProcessingAdapter;
import org.example.fileuploadjob.model.FileModel;
import org.example.fileuploadjob.model.ManufacturerDataModel;
import org.example.fileuploadjob.util.DataFieldName;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Qualifier("JsonFileProcessingAdapter")
public class JsonFileProcessingAdapter implements FileProcessingAdapter {
    private static final Logger log = LoggerFactory.getLogger(JsonFileProcessingAdapter.class);

    @Override
    public FileModel getDataModelList(File file) {
        LocalDateTime processingTime = LocalDateTime.now();
        List<ManufacturerDataModel> models = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>();

        FileModel output = new FileModel();
        output.setModels(models);
        output.setFieldNames(fieldNames);

        JSONArray jsonArr = null;
        try {
             Object obj = new JSONParser().parse(new FileReader(file.getAbsoluteFile()));
             if (obj instanceof JSONArray) {
                 jsonArr = (JSONArray) obj;
             } else if (obj instanceof JSONObject) {
                 jsonArr = new JSONArray();
                 jsonArr.add((JSONObject) obj);
             }
        } catch (Exception e) {
            log.error(file.getAbsoluteFile().toString() + " could not be parsed because : " + e);
            return output;
        }
        String producerUsername = getProducerUsername(file);

        for (int idx = 0; idx < jsonArr.size(); idx++) {
            JSONObject jsonObj = (JSONObject) jsonArr.get(idx);
            Iterator<Map.Entry> itr = jsonObj.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry pair = itr.next();
                ManufacturerDataModel model = new ManufacturerDataModel();
                model.setProducerUsername(producerUsername);
                model.setCommonData(false);
                model.setSourceFile(file.getName());
                model.setDataFieldName(pair.getKey().toString());
                model.setDataFieldValue(pair.getValue().toString());
                model.setStartDateTime(processingTime);
                model.setEndDateTime(processingTime.plusYears(300));
                model.setUploadDateTime(processingTime);
                for (DataFieldName fieldNameEnum: DataFieldName.values()) {
                    if(fieldNameEnum.getSynonyms().stream()
                            .anyMatch(s -> s.replace("//s", "").toUpperCase()
                                    .equals(model.getDataFieldName().replace("//s", "").toUpperCase()))) {
                        model.setDataFieldName(fieldNameEnum.getDataFieldName());
                    }
                }
                models.add(model);
            }
        }

        models.stream().forEach(m -> fieldNames.add(m.getDataFieldName()));
        output.setFieldNames(fieldNames);
        output.setModels(models);
        return output;
    }

}
