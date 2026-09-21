package com.example.imageSaver.service;

 import com.example.imageSaver.dto.ListImageResponseDTO;
 import com.example.imageSaver.models.*;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
 import net.coobird.thumbnailator.Thumbnails;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;

 import java.io.*;
 import java.nio.file.*;
 import java.util.ArrayList;
 import java.util.List;
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

    @Autowired
    public ListImageResponseDTO listImageResponseDTO;


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
        Optional<Category> optionalCategory = categoryModelRepository.findByNameIgnoreCase(getCategoryName);

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
        Optional<Tag> optionalTag = tagModelRepository.findByNameIgnoreCase(getTagName);

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




    public List<ListImageResponse> searchImagesByTitle(String titleName) {
         List<ListImageResponse> listImageResponses = new ArrayList<>();

          List<ImageMetaData> imageResponseData=imageFileUploadRepository.findAllByTitleIgnoreCase(titleName).get();

        List<ListImageResponse> list=listImageResponseDTO.convertToListImageResponse(imageResponseData);


//        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/images/download/")
//                .path( searchImageName)
//                .toUriString();


        for(int i =0 ; i<list.toArray().length ; i++){
           ListImageResponse  listItem=list.get(i);

            listImageResponses.add( listItem);

        }

        return listImageResponses;
    }



    public List<ListImageResponse> searchImagesByTag(String tagName) {
        List<ListImageResponse> listImageResponses = new ArrayList<>();


        Tag imageResponseData=tagModelRepository.findByNameIgnoreCase(tagName).get();
        Long tagId=imageResponseData.getId();

        List<ImageMetaData> imageMetaData=imageFileUploadRepository.findAllByTagId(tagId).get();

        List<ListImageResponse> list=listImageResponseDTO.convertToListImageResponse(imageMetaData);

//        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/images/download/")
//                .path( searchImageName)
//                .toUriString();

        for(int i =0 ; i<list.toArray().length ; i++){
            ListImageResponse  listItem=list.get(i);

            listImageResponses.add( listItem);

        }

        return listImageResponses;
    }


    public List<ListImageResponse> searchImagesByCategory(String tagName) {
        List<ListImageResponse> listImageResponses = new ArrayList<>();


        Category categoryData=categoryModelRepository. findByNameIgnoreCase(tagName).get();
        Long categoryId=categoryData.getId();
        List<ImageMetaData> imageMetaData=imageFileUploadRepository.findAllByCategoryId(categoryId).get();

        List<ListImageResponse> list=listImageResponseDTO.convertToListImageResponse(imageMetaData);

//        String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/images/download/")
//                .path( searchImageName)
//                .toUriString();

        for(int i =0 ; i<list.toArray().length ; i++){
            ListImageResponse  listItem=list.get(i);

            listImageResponses.add( listItem);

        }

        return listImageResponses;
    }






}





