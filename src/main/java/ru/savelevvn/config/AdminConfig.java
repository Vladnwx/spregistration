package ru.savelevvn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:secret.properties")
@ConfigurationProperties(prefix = "app.admin")
public class AdminConfig {
    private String username;
    private String email;
    private String password;
}
