package org.example.fileuploadjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileProcessingJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileProcessingJobApplication.class, args);
	}

}
