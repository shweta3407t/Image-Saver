package com.example.imageSaver.service;

import com.example.imageSaver.dto.UpdateRequestDTO;
import com.example.imageSaver.exception.exceeption.ResourceNotFoundException;
import com.example.imageSaver.models.Category;
import com.example.imageSaver.models.ImageMetaData;
import com.example.imageSaver.models.Tag;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateImageService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;

    public  void  updateImage( UpdateRequestDTO updateRequestDTO){

        ImageMetaData imageMetaData=imageFileUploadRepository.findById(updateRequestDTO.getId())
                .orElseThrow(() ->new ResourceNotFoundException("image not exist with this id" + updateRequestDTO.getId()));


        mapToImageMetaData(updateRequestDTO , imageMetaData);

    }


    public  void  deleteImage(Long id){
        imageFileUploadRepository.deleteById(id);
    }


    public void mapToImageMetaData(UpdateRequestDTO updateRequestDTO , ImageMetaData imageMetaData){

        //title description
        imageMetaData.setTitle(updateRequestDTO.getTitle());
        imageMetaData.setDescription(updateRequestDTO.getDescription());

        //tag
        //TODO;find that tag and category exist before update
        String requestTag= updateRequestDTO.getTag();
        Boolean isTagExist=tagModelRepository.existsByName(requestTag);
        Tag newTag;
        //chack tag exist
        if(!isTagExist){
              newTag=new Tag(requestTag);
            tagModelRepository.save(newTag);
         }else{
            newTag=tagModelRepository.findByNameIgnoreCase(requestTag)
                    .orElseThrow(()-> new RuntimeException("Tag Not Found"));
        }

        imageMetaData.getTag().add(newTag);


        //category
        String requestCategory=updateRequestDTO.getCategory();
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


//how to upload fiff file
    //bulk uploa
    //



}
