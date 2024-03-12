package com.cloud.learningaws.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;



    @Value("${aws.s3.bucketName}")
    public String bucketName;

    public URL generatePreSignedURL(String fileName, HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 3);
        URL url = amazonS3.generatePresignedUrl(bucketName, fileName, calendar.getTime(),httpMethod);
        log.info("----->S3Service----->generatePreSignedURL-----> url: {} ", url);
        return url;
    }

    public void uploadFile(MultipartFile file, URL presignedUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) presignedUrl.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            OutputStream out = connection.getOutputStream();
            InputStream in = file.getInputStream();
            StreamUtils.copy(in, out);
            in.close();
            out.close();
            connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
