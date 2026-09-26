package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.BulkUploadRequestDTO;
import com.example.imageSaver.dto.UploadImageRequestDTO;
import com.example.imageSaver.models.ImageMetaData;
import com.example.imageSaver.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;


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
            "image/avif",
            "image/webp",
            "application/pdf"
//            "application/json"
    );

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String > uploadImage(@ModelAttribute("file") MultipartFile file,
                                               @RequestParam("title") String title,
                                               @RequestParam("description") String description,
                                               @RequestParam("tag") String tag,
                                               @RequestParam("category") String category
    ) throws IOException {
        UploadImageRequestDTO requestDTO=new UploadImageRequestDTO(title, description, category,tag, file);


//    public ResponseEntity<String > uploadImage(
//            @ModelAttribute UploadImageRequestDTO requestDTO
//    ) throws IOException {
//

        // Save the uploaded file and data in db and blob stoage
        if (requestDTO.getFiles().isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        //validate meme type
        String contentType=requestDTO.getFiles().getContentType();

        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("File type not allowed. Allowed types: JPEG, PNG, PDF, PNG, GIF, WEBP , AVIF");
        }

        //bussineaa logic
        try {
             uploadImageService. saveImageFileRequest(requestDTO);
            return ResponseEntity.ok("Image uploaded");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    //bulk save
    @PostMapping(value = "bulkUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> bulkUploadImages(@RequestBody BulkUploadRequestDTO bulkRequest) {

        UploadImageRequestDTO[]  uploadImageArray=bulkRequest.getBulkRequest();
        if(uploadImageArray == null ){
            return ResponseEntity.badRequest().body("No data received(null)");
        }

        //infra logic
        for (UploadImageRequestDTO image :  bulkRequest.getBulkRequest()) {

            if (image.getFiles().isEmpty()) {
                System.out.println("file check");
                return ResponseEntity.badRequest().body("Please select a file to upload.");
            }

            String contentType=image.getFiles().getContentType();

            if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("File type not allowed. Allowed types: JPEG, PNG, PDF, PNG, GIF, WEBP");
            }

            //business logic
            try {
                System.out.println("business logic");
                uploadImageService.saveImageFileRequest(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        return ResponseEntity.ok("Bulk Image uploaded");


    }

}
