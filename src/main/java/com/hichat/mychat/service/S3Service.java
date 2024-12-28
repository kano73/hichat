package com.hichat.mychat.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.nio.file.Path;

// OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
//@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "hichat-users-photos";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String key, Path filePath) {
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(), filePath);

        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
}
