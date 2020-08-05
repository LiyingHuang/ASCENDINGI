package org.liying.controller;

import org.liying.service.FileService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping(value = {"/files"})
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private Logger logger;
    String bucketName = System.getProperty("s3-bucketName");

    // (value = {"/files"})
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file){
        logger.info("test file name: "+ file.getOriginalFilename());
        try{
            return fileService.uploadMulFileWithUUID(bucketName, file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    // (value = {"/files"})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getObject(@RequestParam("fileName") String fileName){
        return fileService.getFileURLString(bucketName, fileName);
    }
}
