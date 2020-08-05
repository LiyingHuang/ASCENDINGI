package com.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
//@Profile({"dev"})
//-Dspring.profiles.active=dev
public class ListenerConfig {
    @Value("${aws.region}")
    private String region;

    //?? 造不了bean
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SQSConnectionFactory getSQSConnectionFactory() {
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(region)
                        .withCredentials(new DefaultAWSCredentialsProviderChain())
        );
        return connectionFactory;
    }
}
