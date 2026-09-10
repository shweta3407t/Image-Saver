package com.example.imageSaver.controllers;


import com.example.imageSaver.models.*;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
import com.example.imageSaver.service.ImageFileUploadService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageFileUploadController {

    @Autowired
    public ImageFileUploadService imageFileUploadService;

    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;

    Path storageDirectory= Paths.get("uploaded-images");




    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String > uploadImage(@ModelAttribute ImageUploadRequest imageUploadRequest) throws IOException {
        // Save the uploaded file and data in db and blob stoage



        ImageUpload uploadImage=new ImageUpload();

        uploadImage.setTitle(imageUploadRequest.getTitle());
        uploadImage.setDiscription(imageUploadRequest.getDescription());


        uploadImage.setCategory(imageUploadRequest.getCategory());

        Tag tag=imageUploadRequest.getTag();
        uploadImage.getTag().add(tag);

        Category categoryModel=imageUploadRequest.getCategory();
        categoryModelRepository.save(categoryModel);

        tagModelRepository.save(tag);


        // 1. Generate a unique name for the final compress file
        MultipartFile files=imageUploadRequest.getFiles();
        System.out.println(files.getOriginalFilename());



        String fileName= UUID.randomUUID() + "_" + files.getOriginalFilename();

        Path targetedLocation=storageDirectory.resolve(fileName);

        Files.copy(files.getInputStream() , targetedLocation , StandardCopyOption.REPLACE_EXISTING);


//compress
        File compressedFile = new File(storageDirectory + "Compressed" + files.getOriginalFilename());
        Thumbnails.of(fileName)
                .scale(1.0)
                .outputQuality(0.6)
                .toFile(compressedFile);

        //convert it into url
        // Dynamically compute the absolute actual browser URL path pointing to our download route
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(fileName)
                .toUriString();


        String compressUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(compressedFile.getName())
                .toUriString();


        uploadImage.setImageUrl(downloadUrl);
        uploadImage.setThumbnailUrl(compressUrl);


        imageFileUploadRepository.save(uploadImage);


    }



    @GetMapping("/search")
    public ResponseEntity<ListImageResponse> searchImageByFileName(@RequestParam String imageTitle){

        return  imageFileUploadRepository.findByTitle(imageTitle)
                .map(image -> ResponseEntity.ok(
                        Collections.singletonMap("imageUrl" ,  image.getImageUrl())))
                .orElse(ResponseEntity.notFound().build());
    }

}
