package com.service;

import com.ConsumerApplication;
import com.Service.ListenerService;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerApplication.class)
public class ListenerServiceTest {
    @Autowired
    private ListenerService listenerService;
    @Autowired
    private SQSConnectionFactory connection;
/*
    1 SQSConnectionFactory connectionFactory = new SQSConnectionFactory(...;
    2 SQSConnection connection = connectionFactory.createConnection();
    3 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    4 MessageConsumer consumer = session.createConsumer(session.createQueue("shopping-platform-queue"));
    5 consumer.setMessageListener(new ListenerService());
    6 connection.start();

    // 大致想法是： verify mock之后的consumer对象调用了setMessageListener()几次来test （如果mock对象选错了，应该怎么选呢？）
       1. mock consumer
          问题1：consumer在待测试的方法 receiveMessageAsync() 中必须预先声明？否则怎么保证测试时的consumer对象是mock之后的？

                我的疑惑：以前做s3时，预先在方法外声明s3Client(比如可以autowired进来), 然后在方法内部先进行一通与s3Client无关的操作，
                然后再用s3Client.(...)调用某个方法，测试时，同样可以autowired一个mock之后的s3Client进来，这个mock对象与方法内部代码
                上文是没有关系的。
          问题2: mock对象(line 4)与方法内部代码上文(line 1,2,3)有关联时有影响吗？

       2. 假如mock consumer成功之后
          问题1：consumer 成为mock对象之后，consumer.setMessageListener(new ListenerService()); 得做stub来避免NPE？

 */
//    @Test
//    public void ListenerTest() throws JMSException {
//        listenerService.receiveMessageAsync();
//        //verify(connection,times(1)).start();
//        //verify(consumer,times(1)).setMessageListener();
//    }
}
