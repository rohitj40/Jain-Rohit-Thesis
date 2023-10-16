package org.example.fileuploadjob.adapter.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.example.fileuploadjob.adapter.FileProcessingAdapter;
import org.example.fileuploadjob.model.Farm;
import org.example.fileuploadjob.model.Farms;
import org.example.fileuploadjob.model.FileModel;
import org.example.fileuploadjob.model.ManufacturerDataModel;
import org.example.fileuploadjob.util.DataFieldName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Qualifier("XMLFileProcessingAdapter")
public class XMLFileProcessingAdapter implements FileProcessingAdapter {
    private static final Logger log = LoggerFactory.getLogger(XMLFileProcessingAdapter.class);
    @Override
    public FileModel getDataModelList(File file) {
        XmlMapper xmlMapper = new XmlMapper();

        LocalDateTime processingTime = LocalDateTime.now();
        List<ManufacturerDataModel> models = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>();

        FileModel output = new FileModel();
        output.setModels(models);
        output.setFieldNames(fieldNames);

        String producerUsername = getProducerUsername(file);

        Farms farms = null;
        try {
           farms = xmlMapper.readValue(file, Farms.class);
        } catch (IOException e) {
            log.error(file.getAbsoluteFile() + " could not be read because : " + e);
            return output;
        }
        List<Field> fields = Arrays.asList(Farm.class.getDeclaredFields());
        if (farms != null && farms.getFarm() != null && !fields.isEmpty()) {
            Arrays.stream(farms.getFarm()).forEach(farm -> {
                for (Field field: fields) {
                    String fieldName = getDataFieldName(field.getName());
                    String fieldValue = getDataFieldValue(farm, field);
                    if (StringUtils.isNotBlank(fieldName) && StringUtils.isNotBlank(fieldValue)) {
                        ManufacturerDataModel model = new ManufacturerDataModel();
                        model.setProducerUsername(producerUsername);
                        model.setCommonData(false);
                        model.setSourceFile(file.getName());
                        model.setDataFieldName(fieldName);
                        model.setDataFieldValue(fieldValue);
                        model.setStartDateTime(processingTime);
                        model.setEndDateTime(processingTime.plusYears(300));
                        model.setUploadDateTime(processingTime);
                        models.add(model);
                    }
                }
            });
        }

        models.stream().forEach(m -> fieldNames.add(m.getDataFieldName()));
        output.setFieldNames(fieldNames);
        output.setModels(models);

        return output;
    }

    private String getDataFieldName(String fieldName) {
        DataFieldName dataFieldNameEnum = getDataFieldNameEnum(fieldName);
        if (dataFieldNameEnum != null) {
            return dataFieldNameEnum.getDataFieldName();
        }
        return null;
    }

    private String getDataFieldValue(Farm farmObject, Field farmObjectFields) {
        if (farmObjectFields != null) {
            try {
                farmObjectFields.setAccessible(true);
                return (String)farmObjectFields.get(farmObject);
            } catch (IllegalAccessException e) {
                log.error(farmObjectFields.getName() + " field value using Reflection could not be extracted " + e);
                return null;
            }
        }
        return null;
    }

    private DataFieldName getDataFieldNameEnum(String fieldName) {
        for (DataFieldName fieldNameEnum: DataFieldName.values()) {
            Optional<String> objectFieldName = fieldNameEnum.getObjectFieldNames().stream()
                    .filter(s -> s.replace("//s", "")
                                    .equalsIgnoreCase(fieldName.replace("//s", ""))).findFirst();
            if (objectFieldName.isPresent()) {
                return  fieldNameEnum;
            }
        }
        return null;
    }
}
