package com.example.demorentalcar.configuration;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class DynamoDBConfig {

    @Bean
    public AmazonDynamoDB getAwsDynamoDB(AppConfig appConfig) {
        if (StringUtils.hasLength(appConfig.getDatabase().getDynamodbEndpoint()) &&
                StringUtils.hasLength(appConfig.getDatabase().getDynamodbRegion())) {
            String region = appConfig.getDatabase().getDynamodbRegion();
            String endpoint = appConfig.getDatabase().getDynamodbEndpoint();


            AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                    new AwsClientBuilder.EndpointConfiguration(endpoint, region);
            return AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(endpointConfiguration)
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .build();
        } else {
            return AmazonDynamoDBClientBuilder.standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .build();
        }

    }

    @Bean
    public DynamoDBMapper getDynamoDBMapper(AmazonDynamoDB client) {

        return new DynamoDBMapper(client);

    }
}
