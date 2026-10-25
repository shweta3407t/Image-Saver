package com.example.imageSaver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3StorageService {



    private final S3Client s3Client;
       private final String bucketName;
      private final String endpoint;

    public  S3StorageService(S3Client s3Client ,
                             @Value("${minio.bucket}") String bucketName ,
                             @Value("${minio.url}") String endpoint){
        this.s3Client=s3Client;
        this.bucketName=bucketName;
        this.endpoint=endpoint;
    }

    public  String uploadFile(MultipartFile file, String key) throws IOException {

        PutObjectRequest putObjectRequest=PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest , RequestBody.fromBytes(file.getBytes()));

        return   endpoint + "/" +  bucketName+"/"+ key ;
    }

    public ResponseBytes<GetObjectResponse> downloadImage (String key) throws IOException {
         GetObjectRequest getObjectRequest =
                            GetObjectRequest.builder()
                                    .bucket(bucketName)
                                    .key(key)
                                    .build();

        return s3Client.getObjectAsBytes(getObjectRequest);
    }


    public  void  deleteImage(String key){
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key).
                build());
    }





}
