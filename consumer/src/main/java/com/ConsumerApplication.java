package com;

import com.Service.ListenerService;
import com.Service.SQSService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com"})
public class ConsumerApplication {
    public static void main(String[] args){
//        ConfigurableApplicationContext app = SpringApplication.run(ComsumerApplication.class, args);
//        SQSService sqsService = app.getBean(SQSService.class);
//        sqsService.receiveMessage();
        SpringApplication.run(ConsumerApplication.class, args);
        ListenerService listenerService = new ListenerService();
        listenerService.receiveMessageAsync();
    }
}

