package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.UploadImageRequestDTO;
import com.example.imageSaver.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/images")
public class UploadImageController {

    @Autowired
    public UploadImageService uploadImageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String > uploadImage(@ModelAttribute("file") MultipartFile file,
                                               @RequestParam String title,
                                               @RequestParam String description,
                                               @RequestParam String tag,
                                               @RequestParam String category
    ) throws IOException {
        // Save the uploaded file and data in db and blob stoage

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
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
