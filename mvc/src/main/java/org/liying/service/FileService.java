package org.liying.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class FileService {
    //@Autowired (Field Dependency Injection)
    private AmazonS3 amazonS3;
    private Logger logger = LoggerFactory.getLogger(getClass());
//    通过环境变量传入bucketName
//    @Value("${liyingjdk-s3-bucket-1}")
//    private String bucketName;
    private String bucketName = System.getProperty("s3-bucketName");
    // 直接传入bucketName
    //String bucketName = "liyingjdk-s3-bucket-1";
    // Constructor Dependency Injection
    public FileService(@Autowired AmazonS3 amazonS3) { this.amazonS3 = amazonS3; }

    public void uploadFile(File file) throws IOException {
        PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
        amazonS3.putObject(request);
    }

    public void uploadFileWithUUID(String bucketName, File file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String[] split = file.getName().split("\\.");
        String fileName = randomUUIDString + "." + split[split.length - 1];
        System.out.println(fileName);
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        amazonS3.putObject(request);
        logger.info("The file name = %s was uploaded to the BUCKET %s", fileName, bucketName);
    }

    public String uploadMulFileWithUUID(String bucketName, MultipartFile file) throws IOException {
        try {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            String[] split = file.getOriginalFilename().split("\\.");
            String fileName = randomUUIDString + file.getName() + "." + split[split.length - 1];
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata);
            amazonS3.putObject(request);
            logger.info("The file name = %s was uploaded to the BUCKET %s", fileName, bucketName);
            return fileName;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    public URL getFileURL(String fileName, String bucketName){
        // Set the pre-signed URL to expire after 24 hours.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 24;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url;
//        String urlString = url.toString(); // 如果用mock，得到的url=null，用toString就会NPE
//        return urlString;
    }
    public String getFileURLString(String bucketName, String fileName){
        // Set the pre-signed URL to expire after 24 hours.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 24;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
