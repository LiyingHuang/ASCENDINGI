package org.liying.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // 使用在service层 类上用于实例化bean
public class AWSS3Service {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private AmazonS3 amazonS3;

    private String myAWSAccessKeyId="AKIAWJ5GI5ZXCAN2JHI5";
    private String myAWSSecretKey="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";

    /*
    public AWSS3Service(){ this.amazonS3= getS3ClientUsingDefaultChain(); } // VM Option
    //public AWSS3Service(){this.amazonS3= getS3ClientWithSuppliedCredentials();} // HardCode
     */

    // Constructor Based Injection
    public AWSS3Service(@Autowired AmazonS3 amazonS3){ this.amazonS3= amazonS3; }

    private AmazonS3 getS3ClientWithSuppliedCredentials() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(myAWSAccessKeyId, myAWSSecretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1")
                .build();
        return s3Client;
    }
    private AmazonS3 getS3ClientUsingDefaultChain() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1")
                .build();
        return s3Client;
    }
    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;
        if(!amazonS3.doesBucketExistV2(bucketName)) {
            bucket = amazonS3.createBucket(bucketName);
        } else {
            logger.info("bucket name: {} is not available."
                    + " Try again with a different Bucket name.", bucketName);
        }
        return bucket;
    }
}


