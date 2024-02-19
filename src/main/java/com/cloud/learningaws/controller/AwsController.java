package com.cloud.learningaws.controller;

import com.amazonaws.HttpMethod;
import com.cloud.learningaws.util.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AwsController {

    private final S3Service s3Service;

    @GetMapping("/api")
    public String getApi() {
        return "This is running on AWS EC2 instance !!";
    }

    @GetMapping("/getUrl")
    public ResponseEntity<String> getPreSignedUrl(@RequestParam String fileName) {
        URL url = s3Service.generatePreSignedURL(fileName, HttpMethod.GET);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(url.toString());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file")MultipartFile pdfFile){
        URL url = s3Service.generatePreSignedURL(pdfFile.getOriginalFilename(), HttpMethod.PUT);
        s3Service.uploadFile(pdfFile, url);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("File uploaded successfully");
    }
}
