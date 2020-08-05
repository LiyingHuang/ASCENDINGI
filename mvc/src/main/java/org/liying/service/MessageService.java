package org.liying.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private AmazonSQS sqsClient;
    private String queueUrl;
    private String queueName = System.getProperty("aws.sqs.name");

//    private String queueName = "shopping-platform-queue";
//    private AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
//            .withCredentials(new DefaultAWSCredentialsProviderChain())
//            .withRegion("us-east-1")
//            .build();
//    private String queueUrl = "https://sqs.us-east-1.amazonaws.com/433603538542/shopping-platform-queue";

    public MessageService(@Autowired AmazonSQS sqsClient){
        this.sqsClient = sqsClient;
        this.queueUrl = getQueueUrl(queueName);
    }
    public String getQueueUrl(String queueName) {
        GetQueueUrlResult getQueueUrlResult = sqsClient.getQueueUrl(queueName);
        logger.info("QueueUrl: " + getQueueUrlResult.getQueueUrl());
        return getQueueUrlResult.getQueueUrl();
    }

    public void sendMessage(String messageBody, Integer delaySec){
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody)
                .withDelaySeconds(delaySec);
        sqsClient.sendMessage(send_msg_request);
    }

}

