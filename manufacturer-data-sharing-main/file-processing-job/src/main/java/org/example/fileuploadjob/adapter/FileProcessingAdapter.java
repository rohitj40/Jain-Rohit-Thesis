package org.example.fileuploadjob.adapter;

import org.example.fileuploadjob.model.FileModel;

import java.io.File;

public interface FileProcessingAdapter {

    public FileModel getDataModelList(File file);

    default String getProducerUsername(File file) {
        String fileName = file.getAbsoluteFile().toString();
        String filepath = fileName.substring(0, fileName.lastIndexOf(File.separatorChar));
        return filepath.substring(filepath.lastIndexOf(File.separatorChar) + 1, filepath.length());
    }
}
