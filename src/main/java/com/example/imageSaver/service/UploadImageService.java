package com.example.imageSaver.service;

 import com.example.imageSaver.dto.ImageMetaDataConverter;
 import com.example.imageSaver.dto.UploadImageResponseDTO;
 import com.example.imageSaver.dto.UploadImageRequestDTO;
 import com.example.imageSaver.exception.exceeption.DuplicateResourceException;
 import com.example.imageSaver.exception.exceeption.ResourceNotFoundException;
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
    public ImageMetaDataConverter listImageMetaDtataConverter;


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






    public void saveImageFileRequest(UploadImageRequestDTO uploadImageRequestDTO) throws IOException {
        ImageMetaData uploadImage = new ImageMetaData();

        //exception
        String title= uploadImageRequestDTO.getTitle();
        Boolean checkTitleExist=imageFileUploadRepository.existsByTitle(title);
        if(checkTitleExist ){
            throw new DuplicateResourceException("Image with title " + title + " already exist.");
        }


        uploadImage.setTitle(uploadImageRequestDTO.getTitle());
        uploadImage.setDescription(uploadImageRequestDTO.getDescription());


        //save and upload category
        String getCategoryName = uploadImageRequestDTO.getCategory();
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
        String getTagName = uploadImageRequestDTO.getTag();
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




         // TODO: create blob file URL
        // http://localhost:8080/files/{file-name}&quality=(low|mid|high)
        MultipartFile multipartFile = uploadImageRequestDTO.getFiles();

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




    public List<UploadImageResponseDTO> searchImagesByTitle(String titleName) {
         List<UploadImageResponseDTO> listImageResponsDTOS = new ArrayList<>();

         List<ImageMetaData> imageResponseData=imageFileUploadRepository
                 .findAllByTitleIgnoreCase(titleName)
                 .orElseThrow(() -> new ResourceNotFoundException("Image of " + titleName +" does not exist"));

        List<UploadImageResponseDTO> list= listImageMetaDtataConverter.convertToListImageResponse(imageResponseData);

        for(int i =0 ; i<list.toArray().length ; i++){
           UploadImageResponseDTO listItem=list.get(i);

            listImageResponsDTOS.add( listItem);

        }

        return listImageResponsDTOS;
    }



    public List<UploadImageResponseDTO> searchImagesByTag(String tagName) {
        List<UploadImageResponseDTO> listImageResponsDTOS = new ArrayList<>();


        Tag imageResponseData=tagModelRepository.findByNameIgnoreCase(tagName)
                .orElseThrow(() -> new ResourceNotFoundException("Image with " + tagName +" does not exist"));

        Long tagId=imageResponseData.getId();

        List<ImageMetaData> imageMetaData=imageFileUploadRepository.findAllByTagId(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Image with " + tagName +" does not exist"));

        List<UploadImageResponseDTO> list= listImageMetaDtataConverter.convertToListImageResponse(imageMetaData);

        for(int i =0 ; i<list.toArray().length ; i++){
            UploadImageResponseDTO listItem=list.get(i);

            listImageResponsDTOS.add( listItem);

        }

        return listImageResponsDTOS;
    }


    public List<UploadImageResponseDTO> searchImagesByCategory(String category) {
        List<UploadImageResponseDTO> listImageResponsDTOS = new ArrayList<>();

        Category categoryData=categoryModelRepository. findByNameIgnoreCase(category)
                .orElseThrow(() -> new ResourceNotFoundException("Image with " + category +" does not exist"));

        Long categoryId=categoryData.getId();
        List<ImageMetaData> imageMetaData=imageFileUploadRepository.findAllByCategoryId(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Image with " + category +" does not exist"));

        List<UploadImageResponseDTO> list= listImageMetaDtataConverter.convertToListImageResponse(imageMetaData);

        for(int i =0 ; i<list.toArray().length ; i++){
            UploadImageResponseDTO listItem=list.get(i);

            listImageResponsDTOS.add( listItem);

        }

        return listImageResponsDTOS;
    }






}





