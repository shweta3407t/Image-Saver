package com.example.imageSaver.service;

 import com.example.imageSaver.models.*;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
 import net.coobird.thumbnailator.Thumbnails;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.core.io.Resource;
 import org.springframework.core.io.UrlResource;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;

 import java.io.*;
 import java.net.MalformedURLException;
 import java.nio.file.*;
 import java.util.Optional;
import java.util.UUID;


@Service
public class UploadImageService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;


    Path thumbnailStorageDirectory  = null;
    Path originalStorageDirectory  = null;



    public UploadImageService(@Value("${file.upload-dir}") String uploadDir ) throws IOException {
        Path upath = Paths.get(uploadDir);
        if (!Files.isDirectory(upath)) {
            Files.createDirectory(upath);
        }

        Path thumbStoreDir = upath.resolve("thumbnails");
        Path ogStoreDir = upath.resolve("originals");

        if (!Files.isDirectory(thumbStoreDir)) {
            Files.createDirectory(thumbStoreDir);
        }

        if (!Files.isDirectory(ogStoreDir)) {
            Files.createDirectory(ogStoreDir);
        }


        this.thumbnailStorageDirectory = thumbStoreDir.toAbsolutePath();
        this.originalStorageDirectory = ogStoreDir.toAbsolutePath();
    }






    public void saveImageFileRequest(ImageUploadRequest imageUploadRequest) throws IOException {
        ImageMetaData uploadImage = new ImageMetaData();

        uploadImage.setTitle(imageUploadRequest.getTitle());
        uploadImage.setDescription(imageUploadRequest.getDescription());


        //save and upload category
        String getCategoryName = imageUploadRequest.getCategory();
        Optional<Category> optionalCategory = categoryModelRepository.findByName(getCategoryName);

        Category newCategory;
        if (optionalCategory.isPresent()) {
            newCategory = optionalCategory.get();
        } else {
            newCategory = new Category();
            newCategory.setName(getCategoryName);
            categoryModelRepository.save(newCategory);
        }
        uploadImage.setCategory(newCategory);


        //save and upload tag
        String getTagName = imageUploadRequest.getTag();
        Optional<Tag> optionalTag = tagModelRepository.findByName(getTagName);

        Tag newTag;
        if (optionalTag.isPresent()) {
            newTag = optionalTag.get();
        } else {
            newTag = new Tag();
            newTag.setName(getTagName);
            tagModelRepository.save(newTag);
        }
        uploadImage.getTag().add(newTag);






        // 1. Generate a unique name for the final compress file
        // TODO: create blob file URL
        // http://localhost:8080/files/{file-name}&quality=(low|mid|high)
        MultipartFile multipartFile = imageUploadRequest.getFiles();

        String thumbnailFileName = UUID.randomUUID() + "-thumbnail-" + multipartFile.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "-original-" + multipartFile.getOriginalFilename();

        Path targetedLocationOfOriginal = originalStorageDirectory.resolve(uniqueFileName);
        Files.copy(multipartFile.getInputStream(), targetedLocationOfOriginal, StandardCopyOption.REPLACE_EXISTING);

        uploadImage.setImageUrl(uniqueFileName);



        Path targetedLocationOfThumbnail = thumbnailStorageDirectory.resolve(thumbnailFileName);
        try {
            Thumbnails.of(multipartFile.getInputStream())
                    .scale(1.0)
                    .outputQuality(0.6)
                    .toFile(targetedLocationOfThumbnail.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Files.copy(multipartFile.getInputStream(), targetedLocationOfThumbnail, StandardCopyOption.REPLACE_EXISTING);

        uploadImage.setThumbnailUrl(thumbnailFileName);

        imageFileUploadRepository.save(uploadImage);


    }




    public  ListImageResponse searchImageByTitle(String imageTitle){

        ImageMetaData imageData=imageFileUploadRepository.findByTitle(imageTitle).orElseThrow(()-> new RuntimeException("Image not found"));

        ListImageResponse response=new ListImageResponse();
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





