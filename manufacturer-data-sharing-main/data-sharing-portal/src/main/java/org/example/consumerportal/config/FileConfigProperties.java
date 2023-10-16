package org.example.consumerportal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix="data.sharing.file")
@Setter
@Getter
public class FileConfigProperties {
    private String location;
    private List<String> extensions;
}
