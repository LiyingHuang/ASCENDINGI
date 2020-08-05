package org.liying.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile({"dev","prod"})
public class AWSConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getAmazonS3(){
 //Default
         AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion("us-east-1")
                .build();
         return s3client;

/*
// hard code
        String myAWSAccessKeyId = System.getProperty("aws.accessKeyId");
        String myAWSSecretKey = System.getProperty("aws.secretKey");
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(myAWSAccessKeyId, myAWSSecretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)) //有这一行就是hardcode,  没有这一行就是default，自动去vm里拿id，key
                .withRegion("us-east-1")
                .build();
        return s3Client;

*/
    }
}

