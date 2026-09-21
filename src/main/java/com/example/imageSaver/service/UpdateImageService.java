package com.example.imageSaver.service;

import com.example.imageSaver.dto.UpdateRequestDTO;
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

    public  void  updateImage( Long id ,UpdateRequestDTO updateRequestDTO){

        ImageMetaData imageMetaData=imageFileUploadRepository.findById(id).orElseThrow(() ->new RuntimeException("image not exist with this id"));

//        ImageMetaData updateImage=new ImageMetaData();

        imageMetaData.setTitle(updateRequestDTO.getTitle());
        imageMetaData.setDescription(updateRequestDTO.getDescription());

        Tag newTag=tagModelRepository.findByNameIgnoreCase(updateRequestDTO.getTag()).orElseThrow(
                () ->new RuntimeException("image not exist with this id"));
        imageMetaData.getTag().add(newTag);

        Category category=categoryModelRepository.findByNameIgnoreCase(updateRequestDTO.getCategory());
        imageMetaData.setCategory(updateRequestDTO.getCategory());



    }


    public  void  deleteImage(Long id){
        imageFileUploadRepository.deleteById(id);
    }
}
