package com.example.imageSaver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3StorageService {
    private final S3Client s3Client;
    private final String bucketName;
    private final String endpoint;

    public  S3StorageService(S3Client s3Client , @Value("${minio.bucket}") String bucketName , String endpoint){
        this.s3Client=s3Client;
        this.bucketName=bucketName;
        this.endpoint=endpoint;
    }

    public  String uploadFile(MultipartFile file, String filePath){

        PutObjectRequest putObjectRequest=PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest , RequestBody.fromBytes(file.getBytes()));

        return   endpoint + "/" +  bucketName+"/"+filePath ;
    }

}
