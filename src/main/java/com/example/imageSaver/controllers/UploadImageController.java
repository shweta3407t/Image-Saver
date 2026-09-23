package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.UploadImageRequestDTO;
import com.example.imageSaver.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/images")
public class UploadImageController {

    @Autowired
    public UploadImageService uploadImageService;


    //meme type
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/jpg",
            "image/gif",
            "image/webp",
            "application/pdf"
    );

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String > uploadImage(@ModelAttribute("file") MultipartFile file,
                                               @RequestParam("title") String title,
                                               @RequestParam("description") String description,
                                               @RequestParam("tag") String tag,
                                               @RequestParam("category") String category
    ) throws IOException {
        // Save the uploaded file and data in db and blob stoage

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        //validate meme type
        String contentType=file.getContentType();

        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("File type not allowed. Allowed types: JPEG, PNG, PDF, PNG, GIF, WEBP");
        }


        UploadImageRequestDTO uploadImageRequestDTO =new UploadImageRequestDTO(title,description,category,tag,file);

        try {
             uploadImageService. saveImageFileRequest(uploadImageRequestDTO);
            return ResponseEntity.ok("Image uploaded");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }




    }





}
