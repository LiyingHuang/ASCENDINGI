package org.liying.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.liying.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class ImageService {
    private AmazonS3 amazonS3;
    public ImageService(@Autowired AmazonS3 amazonS3){
        this.amazonS3 = amazonS3;
    }
    private String bucketName = System.getProperty("s3-bucketName");
    private Logger logger = LoggerFactory.getLogger(getClass());


    public String saveImage(MultipartFile multipartFile) throws IOException {
        try{
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            String[] split = multipartFile.getOriginalFilename().split("\\.");
            String extension = split[split.length - 1];
            String s3key = randomUUIDString + multipartFile.getName() + "." + extension;
            // Save File information to Image Table

            Image image = new Image();
            image.setExtension(extension);
            image.setFileName(multipartFile.getOriginalFilename());
            image.setBucketName(bucketName);
            //image.setUser();


            // Upload file to AWS S3
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            PutObjectRequest request = new PutObjectRequest(bucketName, s3key, multipartFile.getInputStream(), objectMetadata);

            amazonS3.putObject(request);
            logger.info("The file name = %s was uploaded to the BUCKET %s", s3key, bucketName);

            return s3key;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
}
