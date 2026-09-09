package com.example.imageSaver.service;

import com.example.imageSaver.models.Category;
import com.example.imageSaver.models.ImageUploadRequest;
import com.example.imageSaver.models.ImageUpload;
import com.example.imageSaver.models.Tag;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class ImageFileUploadService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;

    private static final  String UPLOAD_DIR = "upload/" ;




    @EntityGraph(attributePaths = "tag")
    public void saveImageFileRequest(ImageUploadRequest imageUploadRequest) throws IOException {
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
        files.getOriginalFilename();

        File dir = new File(UPLOAD_DIR);
        if(!dir.exists()) {dir.mkdirs(); }

        String fileName= UUID.randomUUID() + "_" + files.getOriginalFilename();

        File originalFile = new File(UPLOAD_DIR + "Original_" + files.getOriginalFilename());
        files.transferTo(originalFile);

        File compressedFile = new File(UPLOAD_DIR + "Compressed" + files.getOriginalFilename());
        Thumbnails.of(originalFile)
                .scale(1.0)
                .outputQuality(0.6)
                .toFile(compressedFile);

        //convert it into url
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(originalFile.getName())
                .toUriString();

        String compressUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(compressedFile.getName())
                .toUriString();


        uploadImage.setImageUrl(downloadUrl);
        uploadImage.setThumbnailUrl(compressUrl);


        imageFileUploadRepository.save(uploadImage);


    }


}
