package com.config;

import com.amazon.sqs.javamessaging.SQSConnection;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import static org.mockito.Mockito.mock;

@Configuration
//@Profile({"unit"})
public class ListenTestConfig {
//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public SQSConnection getConnection(){
//        return mock(SQSConnection.class);
//    }
}
