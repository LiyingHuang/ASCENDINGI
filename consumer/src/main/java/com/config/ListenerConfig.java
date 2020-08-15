package com.config;

import com.Service.ListenerService;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import javax.jms.*;

@Configuration
//@Profile({"dev"})
//-Dspring.profiles.active=dev
public class ListenerConfig {
    @Value("${aws.region}")
    private String region;

    // 分2步造bean：
    // 1 SQSConnectionFactory
//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public SQSConnectionFactory getSQSConnectionFactory() {
//        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
//                new ProviderConfiguration(),
//                AmazonSQSClientBuilder.standard()
//                        .withRegion(region)
//                        .withCredentials(new DefaultAWSCredentialsProviderChain())
//        );
//        return connectionFactory;
//    }


    // 2 SQSConnection
//    @Bean
//    public SQSConnection getJMSConnection(@Autowired SQSConnectionFactory connectionFactory) throws JMSException {
//        SQSConnection connection = connectionFactory.createConnection();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        // Create a consumer for queue
//        MessageConsumer consumer = session.createConsumer(session.createQueue("shopping-platform-queue"));
//        // Instantiate and set the message listener for the consumer
//        consumer.setMessageListener(new ListenerService());
//        return connection;
//    }
}
