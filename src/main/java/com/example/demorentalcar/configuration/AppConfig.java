package com.example.demorentalcar.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @NotNull
    private String apiKey;

    @NotNull
    private DataBaseConfig database;

    @Data
    @NoArgsConstructor
    @Configuration
    public static class DataBaseConfig {

        private String dynamodbEndpoint;
        private String dynamodbRegion;

    }

}
