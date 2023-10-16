package org.example.consumerportal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="data.sharing.app")
@Setter
@Getter
public class ApplicationConfigProperties {
    private Boolean showgranteddataindashboard;
}
