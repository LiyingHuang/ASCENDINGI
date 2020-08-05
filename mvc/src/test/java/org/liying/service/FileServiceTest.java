package org.liying.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liying.ApplicationBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.net.URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;
    @Autowired
    private AmazonS3 amazonS3;
    private String bucketName = System.getProperty("s3-bucketName");
    private File file = new File("/Users/liyinghuang/Downloads/Java Interview Question.pdf");

    MultipartFile multipartFile = mock(MultipartFile.class);

    @Test
    public void uploadFileTest() throws Exception {
        fileService.uploadFile(file);
        //Assert.assertTrue(false);
    }
    @Test
    public void uploadFileMockTest() throws Exception {
        fileService.uploadFile(file);
        verify(amazonS3,times(1)).putObject(any(PutObjectRequest.class));
    }
    @Test
    public void uploadFileWithUUIDTest() throws Exception{
        fileService.uploadFileWithUUID(bucketName, file);
        //verify(amazonS3,times(1)).putObject(any(PutObjectRequest.class));
    }
    @Test
    public void uploadMulFileWithUUIDTest() throws Exception{
        fileService.uploadMulFileWithUUID(bucketName, multipartFile);
        verify(amazonS3,times(1)).putObject(any(PutObjectRequest.class));
    }
    @Test
    public void getFileURLTest() throws Exception{
        URL url = fileService.getFileURL("/Users/liyinghuang/Downloads/Java Interview Question.pdf", bucketName);
        // String urlString = url.toString();
        // Mock verify
        verify(amazonS3,times(1)).generatePresignedUrl(any(GeneratePresignedUrlRequest.class));
    }
}
