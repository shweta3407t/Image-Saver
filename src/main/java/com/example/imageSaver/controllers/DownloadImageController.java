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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image/download")
@CrossOrigin(origins = "*")

public class DownloadImageController {

    @Autowired
    public S3StorageService s3StorageService;


    @GetMapping("/original")
    public ResponseEntity<Resource> downloadImage(@RequestParam String originalImagUrl) throws IOException {

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

    @GetMapping("/thumbnail")
    public ResponseEntity<Resource> showThumbnailImage(@RequestParam String thumbnailUrl) throws IOException {

        System.out.println("pppppppppppppppppppppp");

        //get image from mimio
        ResponseBytes<GetObjectResponse> imageFile = s3StorageService.showImage(thumbnailUrl);
        byte[] data= imageFile.asByteArray();
        ByteArrayResource resource = new ByteArrayResource(data);

        //get content type
        String contentType = imageFile.response().contentType();
        MediaType mediaType=contentType !=  null
                ? MediaType.parseMediaType(contentType)
                : MediaType.APPLICATION_OCTET_STREAM;

        System.out.println("tttttttttttttttttttttttttttt");

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" " + resource.getFilename() + "\"") // <-- Displays inside the browser frame
                .body(resource);
    }
///image is not downloading and image is not showing in frontend


}
