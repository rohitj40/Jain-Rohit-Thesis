package org.example.fileuploadjob.tasks;

import org.example.fileuploadjob.service.FileProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileProcessingScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(FileProcessingScheduledTasks.class);

    @Autowired
    private FileProcessingService fileProcessingService;

    @Scheduled(fixedRate = 3600000)
    public void reportCurrentTime() {
        fileProcessingService.processFiles();
    }
}
