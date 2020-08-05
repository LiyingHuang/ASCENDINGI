package com.Service;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.jms.*;

@Service
public class ListenerService implements MessageListener {

    @Autowired
    private SQSConnectionFactory connectionFactory;

    @Override
    public void onMessage(Message message) {
        try {
            // Cast the received message as TextMessage and print the text to screen.
            System.out.println("Received: " + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void receiveMessageAsync(){
        try{

            // Create connection factory
            SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                    new ProviderConfiguration(),
                    AmazonSQSClientBuilder.standard()
                            .withRegion("us-east-1")
                            .withCredentials(new DefaultAWSCredentialsProviderChain())
            );

            // Create connection
            SQSConnection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create a consumer for queue
            MessageConsumer consumer = session.createConsumer(session.createQueue("shopping-platform-queue"));
            // Instantiate and set the message listener for the consumer
            consumer.setMessageListener(new ListenerService());
            // Start receiving incoming messages.
            connection.start();
            System.out.println( "Connection start..." );
            Thread.sleep(120000); // 2min
            connection.close();
            System.out.println( "Connection closed." );
        } catch (InterruptedException | JMSException e){
            e.printStackTrace();
        }
    }

    /*
    public void receiveMessageAsync(){
        try{
            // Create connection factory
            SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                    new ProviderConfiguration(),
                    AmazonSQSClientBuilder.standard()
                            .withRegion("us-east-1")
                            .withCredentials(new DefaultAWSCredentialsProviderChain())
            );
            // Create connection
            SQSConnection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create a consumer for queue
            MessageConsumer consumer = session.createConsumer(session.createQueue("shopping-platform-queue"));
            // Instantiate and set the message listener for the consumer
            consumer.setMessageListener(new ListenerService());
            // Start receiving incoming messages.
            connection.start();
            System.out.println( "Connection start..." );
            Thread.sleep(120000); // 2min
            connection.close();
            System.out.println( "Connection closed." );
        } catch (InterruptedException | JMSException e){
            e.printStackTrace();
        }
    }
    */
}
