package com.example.imageSaver.service;

import com.example.imageSaver.models.*;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;


@Service
public class ImageFileUploadService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;

    Path storageDirectory= Paths.get("./uploaded-images");

    public void saveImageFileRequest(ImageUploadRequest imageUploadRequest) throws IOException {
        ImageUpload uploadImage=new ImageUpload();

        uploadImage.setTitle(imageUploadRequest.getTitle());
        uploadImage.setDiscription(imageUploadRequest.getDescription());


        uploadImage.setCategory(imageUploadRequest.getCategory());

        Tag tag=imageUploadRequest.getTag();
        uploadImage.getTag().add(tag);


        Category category=imageUploadRequest.getCategory();
        categoryModelRepository.save(category);

        tagModelRepository.save(tag);





        // 1. Generate a unique name for the final compress file
        MultipartFile files=imageUploadRequest.getFiles();
        System.out.println(files.getOriginalFilename());


        //create folder if not exist
        if( !Files.exists(storageDirectory)){
            Files.createDirectory(storageDirectory);
        }

        String fileName= UUID.randomUUID() + "_" + files.getOriginalFilename();

        Path targetedLocation=storageDirectory.resolve(fileName);

       Files.copy(files.getInputStream() , targetedLocation , StandardCopyOption.REPLACE_EXISTING);


        //compress
        File compressedFile = new File(storageDirectory + "Compressed" + files.getOriginalFilename());
        Thumbnails.of(fileName)
                .scale(1.0)
                .outputQuality(0.6)
                .toFile(compressedFile);

        //copy file content in folder
        Files.copy(compressedFile.toPath() ,  targetedLocation , StandardCopyOption.REPLACE_EXISTING);

//        //convert it into url
//         String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/files/download/")
//                .path(fileName)
//                .toUriString();
//
//
//        String compressUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/files/download/")
//                .path(compressedFile.getName())
//                .toUriString();


//        uploadImage.setImageUrl(downloadUrl);
        uploadImage.setThumbnailUrl(compressedFile.toString());

        imageFileUploadRepository.save(uploadImage);


    }


    public  ListImageResponse searchImageByTitle(String imageTitle){

        ListImageResponse response=new ListImageResponse();

        ImageUpload imageData=imageFileUploadRepository.findByTitle(imageTitle);

        response.setTitle(imageData.getTitle());
        response.setTag(imageData.getTag().toString());
        response.setCategory(imageData.getCategory().getName());
        response.setThumbnailUrl(imageData.getThumbnailUrl());

        return  response;

    }

    public  ListImageResponse searchImageByTag(String tag){

        ListImageResponse response=new ListImageResponse();

        ImageUpload imageData=imageFileUploadRepository.findByTitle(tag);

        response.setTitle(imageData.getTitle());
        response.setTag(imageData.getTag().toString());
        response.setCategory(imageData.getCategory().getName());
        response.setThumbnailUrl(imageData.getThumbnailUrl());

        return  response;

    }
    public  ListImageResponse searchImageByCategory(String category){

        ListImageResponse response=new ListImageResponse();

        ImageUpload imageData=imageFileUploadRepository.findByTitle(category);

        response.setTitle(imageData.getTitle());
        response.setTag(imageData.getTag().toString());
        response.setCategory(imageData.getCategory().getName());
        response.setThumbnailUrl(imageData.getThumbnailUrl());

        return  response;

    }



 }


