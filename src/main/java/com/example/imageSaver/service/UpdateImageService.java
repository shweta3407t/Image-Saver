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

    public  void  updateImage( UpdateRequestDTO updateRequestDTO){

//      ConvertToImageMetaData( updateRequestDTO);

        Long  id=updateRequestDTO.getId();

        ImageMetaData imageMetaData=imageFileUploadRepository.findById(id).orElseThrow(() ->new RuntimeException("image not exist with this id"));

        //title description
        imageMetaData.setTitle(updateRequestDTO.getTitle());
        imageMetaData.setDescription(updateRequestDTO.getDescription());

        //tag
        Tag newTag=new Tag(updateRequestDTO.getTag());
        tagModelRepository.save(newTag);
        imageMetaData.getTag().add(newTag);

        //category
        Category newCategory= new Category(updateRequestDTO.getCategory());
        newCategory.setName( newCategory.getName());
        categoryModelRepository.save(newCategory);
        imageMetaData.setCategory(newCategory);



        imageFileUploadRepository.save(imageMetaData);




    }


    public  void  deleteImage(Long id){
        imageFileUploadRepository.deleteById(id);
    }





//    public ImageMetaData ConvertToImageMetaData(UpdateRequestDTO updateRequestDTO){
//
//
//
//
//    }
}
