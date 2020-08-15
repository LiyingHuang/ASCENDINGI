package com;

import com.Service.ListenerService;
import com.Service.SQSService;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.jms.JMSException;

@SpringBootApplication(scanBasePackages = {"com"})
public class ConsumerApplication {
    public static void main(String[] args){
//        ConfigurableApplicationContext app = SpringApplication.run(ConsumerApplication.class, args);
//        SQSConnection sqsConnection = app.getBean(SQSConnection.class);
//        try {
//            sqsConnection.start();
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }

         SpringApplication.run(ConsumerApplication.class, args);
   //     ListenerService listenerService = new ListenerService();
   //     listenerService.receiveMessageAsync();
    }
}

