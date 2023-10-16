package org.example.fileuploadjob.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix="file.processing.file")
@Setter
@Getter
public class FileConfigProperties {
    private String location;
    private String archivelocation;
    private List<String> extensions;
    private Boolean savetodb;
    private Boolean toarchive;
}
