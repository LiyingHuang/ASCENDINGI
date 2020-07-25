package org.liying.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {
    //@Autowired (Field Dependency Injection)
    private AmazonS3 amazonS3;

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
        //String[] split = file.getName().split("\\.");
        // String fileName = randomUUIDString + file.getName() + "." + split[split.length - 1];
        String fileName = randomUUIDString + file.getName();
        System.out.println(fileName);
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        amazonS3.putObject(request);
    }
    public void uploadMulFileWithUUID(String bucketName, MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
//        String[] split = file.getName().split("\\.");
//        String fileName = randomUUIDString + file.getName() + "." + split[split.length - 1];
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file.getInputStream(),objectMetadata);
        amazonS3.putObject(request);
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
}
