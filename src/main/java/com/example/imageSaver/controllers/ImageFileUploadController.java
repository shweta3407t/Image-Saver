package com.example.imageSaver.controllers;


import com.example.imageSaver.models.ImageUpload;
import com.example.imageSaver.models.ImageUploadRequest;
import com.example.imageSaver.service.ImageFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageFileUploadController {

    @Autowired
    public ImageFileUploadService imageFileUploadService;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String > uploadImage(@ModelAttribute ImageUploadRequest imageUploadRequest) throws IOException {
        // Save the uploaded file and data in db and blob stoage

        imageFileUploadService.saveImageFileRequest(imageUploadRequest);

        return ResponseEntity.ok("Image uploaded successfully");
    }
}
