package com.example.imageSaver.service;

 import com.example.imageSaver.models.*;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.jspecify.annotations.NonNull;
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
import java.util.Optional;
import java.util.UUID;


@Service
public class ImageFileUploadService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;


    Path originalStorageDirectory= Paths.get("./uploads/originals/");
    Path thumbnailStorageDirectory=Paths.get("./uploaded/thumbnails/");

    public void saveImageFileRequest( ImageUploadRequest imageUploadRequest) throws IOException {
        ImageUpload uploadImage=new ImageUpload();

        uploadImage.setTitle(imageUploadRequest.getTitle());
        uploadImage.setDescription(imageUploadRequest.getDescription());



        //save and upload category
        String getCategoryName=imageUploadRequest.getCategory();
        Optional<Category> optionalCategory=categoryModelRepository.findByName(getCategoryName);

        Category newCategory;
        if(optionalCategory.isPresent()){
            newCategory=optionalCategory.get();
        }
        else {
            newCategory=new Category();
            newCategory.setName(getCategoryName);
            categoryModelRepository.save(newCategory);
        }
        uploadImage.setCategory(newCategory);


        //save and upload tag
        String getTagName=imageUploadRequest.getTag();
        Optional<Tag> optionalTag=tagModelRepository.findByName(getTagName);

        Tag newTag;
        if(optionalTag.isPresent()){
            newTag=optionalTag.get();
        }
        else {
            newTag=new Tag();
            newTag.setName(getTagName);
            tagModelRepository.save(newTag);
        }
        uploadImage.getTag().add(newTag);




         if( !Files.exists(originalStorageDirectory)){
            Files.createDirectories(originalStorageDirectory);
        }
        // 1. Generate a unique name for the final compress file
        MultipartFile multipartFiles=imageUploadRequest.getFiles();
        String uniqueFileName= UUID.randomUUID() + "-original-" + multipartFiles.getOriginalFilename();

        Path targetedLocationOfOriginal=originalStorageDirectory.resolve(multipartFiles.getOriginalFilename());
        Files.copy(originalStorageDirectory , targetedLocationOfOriginal , StandardCopyOption.REPLACE_EXISTING);

        uploadImage.setImageUrl(uniqueFileName);



        //compress

        if(!Files.exists(thumbnailStorageDirectory)){
            Files.createDirectories(thumbnailStorageDirectory);
        }
        File compressedFile = new File(   thumbnailStorageDirectory + "-compressed-" + multipartFiles.getOriginalFilename());

       try {
           Thumbnails.of(multipartFiles.getInputStream())
                   .scale(1.0)
                   .outputQuality(0.6)
                   .toFile(compressedFile);
       }catch (IOException e){
           e.printStackTrace();
       }

        Path targetedLocationOfThumbnail=thumbnailStorageDirectory.resolve( multipartFiles.getOriginalFilename());
        Files.copy(thumbnailStorageDirectory , targetedLocationOfThumbnail , StandardCopyOption.REPLACE_EXISTING);
        uploadImage.setThumbnailUrl(compressedFile.toString());

        imageFileUploadRepository.save(uploadImage);










    }


    public  ListImageResponse searchImageByTitle(String imageTitle){

        ListImageResponse response=new ListImageResponse();

        ImageUpload imageData=imageFileUploadRepository.findByTitle(imageTitle).orElseThrow(()-> new RuntimeException("Image not found"));

        response.setTitle(imageData.getTitle());
        response.setTag(imageData.getTag().toString());
        response.setCategory(imageData.getCategory().getName());
        response.setThumbnailUrl(imageData.getThumbnailUrl());

        return  response;

    }

    public  ListImageResponse searchImageByTag(String tag){

        ListImageResponse response=new ListImageResponse();

        Boolean isImageTagExist=tagModelRepository.existsByName(tag);
        if(isImageTagExist){

        }

        return  response;

    }
    public  ListImageResponse searchImageByCategory(String category){

        ListImageResponse response=new ListImageResponse();

        return  response;

    }



 }


