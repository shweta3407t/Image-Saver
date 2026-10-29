package com.example.imageSaver.controllers;

import com.example.imageSaver.service.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectAclResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image/download")
@CrossOrigin(origins = "*")

public class DownloadImageController {

    @Autowired
    public S3StorageService s3StorageService;


    @GetMapping("/{originalImageUrl}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String originalImagUrl) throws IOException {

        //get image from mimio
        ResponseBytes<GetObjectResponse> imageFile = s3StorageService.downloadImage(originalImagUrl);
         byte[] data= imageFile.asByteArray();
        ByteArrayResource resource = new ByteArrayResource(data);

        //get content type
        String contentType = imageFile.response().contentType();
        MediaType mediaType=contentType !=  null
                ? MediaType.parseMediaType(contentType)
                : MediaType.APPLICATION_OCTET_STREAM;


        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" " + resource.getFilename() + "\"")
                .body(resource);
    }



}
