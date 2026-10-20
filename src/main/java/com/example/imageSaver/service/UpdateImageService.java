package com.example.imageSaver.service;

import com.example.imageSaver.dto.UpdateImageRequestDTO;
import com.example.imageSaver.exception.ResourceNotFoundException;
import com.example.imageSaver.models.Category;
import com.example.imageSaver.models.ImageMetaData;
import com.example.imageSaver.models.Tag;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateImageService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;

    @Transactional
    public  String  updateImage( UpdateImageRequestDTO updateImageRequestDTO){


//
//        String imageMetaData=imageFileUploadRepository.existsById(updateImageRequestDTO.getId());
//                .orElseThrow(() ->new ResourceNotFoundException("image not exist with this id" + updateImageRequestDTO.getId()));


         if(imageFileUploadRepository.existsById(updateImageRequestDTO.getId()){
             mapToImageMetaData(updateImageRequestDTO);
             return "image updated ";

         }

             return "image with "+updateImageRequestDTO.getId() + "does not exist";



     }


    @Transactional
    public String  deleteImage(Long id){
        if(imageFileUploadRepository.existsById(id)){
            imageFileUploadRepository.deleteById(id);
            return "Image deleted ";
        }

        return "Image with this id does not exist";


    }


    public void mapToImageMetaData(UpdateImageRequestDTO updateImageRequestDTO ){

        ImageMetaData imageMetaData= new ImageMetaData();

        //title description
        imageMetaData.setUpdatedAt(LocalDateTime.now());
        imageMetaData.setTitle(updateImageRequestDTO.getTitle());
        imageMetaData.setDescription(updateImageRequestDTO.getDescription());

        //tag
        //TODO;find that tag and category exist before update
        String requestTag= updateImageRequestDTO.getTag();
        Boolean isTagExist=tagModelRepository.existsByName(requestTag);
        Tag newTag;
        //chack tag exist
        if(!isTagExist){
              newTag=new Tag(requestTag);
            tagModelRepository.save(newTag);
         }else{
            newTag=tagModelRepository.findByNameIgnoreCase(requestTag)
                    .orElseThrow(()-> new ResourceNotFoundException("Tag Not Found"));
        }

        imageMetaData.getTag().add(newTag);


        //category
        String requestCategory= updateImageRequestDTO.getCategory();
        Boolean isCategoryExist=categoryModelRepository.existsByName(requestCategory);
        Category newCategory;

        if(!isCategoryExist){
            newCategory= new Category(requestCategory);
            categoryModelRepository.save(newCategory);
        }else{
            newCategory=categoryModelRepository.findByNameIgnoreCase(requestCategory)
                    .orElseThrow(()-> new RuntimeException("Tag Not Found"));
        }

        imageMetaData.setCategory(newCategory);

        imageFileUploadRepository.save(imageMetaData);

    }



}
