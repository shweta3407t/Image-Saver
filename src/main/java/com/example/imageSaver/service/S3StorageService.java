package com.example.imageSaver.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3StorageService {
    private final S3Client s3Client;
    private final String bucketName;
    private final String endpoint;

    public S3StorageService(
            S3Client s3Client,
            @Value("${minio.bucket-name}") String bucketName,
            @Value("${minio.endpoint}") String endpoint
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.endpoint = endpoint;
    }

    public String upload(MultipartFile file, String filePath) throws IOException {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(filePath)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return  endpoint + "/" + bucketName + "/" + filePath;
    }

    public byte[] download(String key) throws IOException {
        try (ResponseInputStream<GetObjectResponse> stream =
                     s3Client.getObject(
                             GetObjectRequest.builder()
                                     .bucket(bucketName)
                                     .key(key)
                                     .build())) {
            return stream.readAllBytes();
        }
    }

    public void delete(String key) {
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );
    }

}
