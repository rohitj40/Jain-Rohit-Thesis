package org.example.consumerportal;

import org.example.consumerportal.config.FileConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileConfigProperties.class)
public class ConsumerPortalWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerPortalWebContentApplication.class, args);
    }

}
