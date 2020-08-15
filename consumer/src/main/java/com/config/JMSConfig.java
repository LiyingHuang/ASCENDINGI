package com.config;

import com.Service.ProcessService;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.sendgrid.SendGrid;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import javax.jms.Session;

@Configuration
@EnableJms
public class JMSConfig {
    @Value("${aws.region}")
    private String region; //private String region = "us-east-1";
    String ACCOUNT_SID = System.getProperty("account_sid");
    String AUTH_TOKEN = System.getProperty("auth_token");
    // AmazonSQS
    @Bean
    public AmazonSQS getAmazoneSQS() {
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.US_EAST_1).build();
    }
    // SendGrid
    @Bean
    public SendGrid getSendGridBean(@Value("${sendgrid.apiKey}") String apiKey){
        return new SendGrid(apiKey);
    }

    // SMS
//    @Bean
//    public ProcessService getProcessService() {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        ProcessService processService = new ProcessService();
//        return processService;
//    }
    //Listener
    @Bean(name = "connectionFactory")
    public SQSConnectionFactory getSQSConnectionfactory(){
        AmazonSQS amazonSQSclient = AmazonSQSClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(region)
                .build();
        SQSConnectionFactory factory = new SQSConnectionFactory(new ProviderConfiguration(),amazonSQSclient);
        return  factory;
    }
    @Bean
    public JmsTemplate getJmsTemplate(@Autowired SQSConnectionFactory sqsConnectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
        return  jmsTemplate;
    }
    @Bean("DynamicResolver")
    public DynamicDestinationResolver getTopicDynamicDestinationResolver(){
        return new DynamicDestinationResolver();
    }

    @Bean(name="jmsListenerContainerFactory")
    @DependsOn({"connectionFactory", "DynamicResolver"})
    public DefaultJmsListenerContainerFactory getDefaultJmsListenerContainerFactory
            (@Autowired SQSConnectionFactory connectionFactory,
             @Autowired DynamicDestinationResolver dynamicDestinationResolver){
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setSessionTransacted(false);
        jmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        jmsListenerContainerFactory.setDestinationResolver(dynamicDestinationResolver);
        jmsListenerContainerFactory.setConcurrency("1");
        jmsListenerContainerFactory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return jmsListenerContainerFactory;
    }
}
