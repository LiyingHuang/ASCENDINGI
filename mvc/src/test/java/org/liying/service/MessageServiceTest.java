//package org.liying.service;
//
//import com.amazonaws.services.sqs.AmazonSQS;
//import com.amazonaws.services.sqs.model.SendMessageRequest;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.liying.ApplicationBootstrap;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= ApplicationBootstrap.class)
//public class MessageServiceTest {
//    @Autowired
//    private MessageService messageService;
//
//    @Autowired
//    private AmazonSQS sqsClient;
//    private String queueUrl;
//    private String queueName = System.getProperty("aws.sqs.name"); //shopping-platform-queue
//
//    @Test
//    public void sendMessageTest(){
//        messageService.sendMessage("test",1);
//    }
//
//    @Test
//    public void getQueueUrlTest(){
//        messageService.getQueueUrl("123");
//        verify(sqsClient,times(1)).getQueueUrl("123");
//    }
//    @Test
//    public void sendMessageTestWithMock(){
//        messageService.sendMessage("test",1);
//        SendMessageRequest sendMessageRequest = new SendMessageRequest()
//                .withQueueUrl(queueUrl)
//                .withMessageBody("test")
//                .withDelaySeconds(1);
////        SendMessageRequest sendMessageRequest = mock(SendMessageRequest.class);
//        verify(sqsClient,times(1)).sendMessage(sendMessageRequest);
//    }
//    @After
//    public void tearDown(){
//     reset(sqsClient);
//    }
//}
