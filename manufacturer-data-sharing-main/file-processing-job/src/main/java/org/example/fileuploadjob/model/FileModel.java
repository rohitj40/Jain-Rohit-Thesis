package org.example.fileuploadjob.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FileModel {
    private Set<String> fieldNames;
    private List<ManufacturerDataModel> models;
}
