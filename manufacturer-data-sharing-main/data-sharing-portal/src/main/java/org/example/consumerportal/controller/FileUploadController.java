package org.example.consumerportal.controller;

import org.example.consumerportal.exception.StorageException;
import org.example.consumerportal.response.model.FileUploadModel;
import org.example.consumerportal.service.FileUploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

    @Autowired
    private FileUploadServiceImpl fileUploadService;

    @PostMapping("/file")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
                                              @RequestParam("userName") String producerUserName, RedirectAttributes redirectAttributes) {

        String status = fileUploadService.storeFile(file, producerUserName);

        FileUploadModel model = new FileUploadModel();
        model.setResponse(status);
        return ResponseEntity.ok(model);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageException exc) {
        return ResponseEntity.ok().build();
    }
}
