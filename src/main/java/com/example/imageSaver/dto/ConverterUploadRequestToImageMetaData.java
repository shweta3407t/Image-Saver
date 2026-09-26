package com.example.imageSaver.dto;

import com.example.imageSaver.models.ImageMetaData;
import com.example.imageSaver.models.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterUploadRequestToImageMetaData {

    public List<UploadImageResponseDTO> convertToListImageResponse (List<ImageMetaData> imageMetaData){

        List<UploadImageResponseDTO> listImageResponsDTOS =new ArrayList<>();

        for(int i=0 ; i<imageMetaData.toArray().length  ; i++){
            UploadImageResponseDTO response=new UploadImageResponseDTO();

            ImageMetaData data=imageMetaData.get(i);

            response.setDescription(data.getDescription());
            response.setTitle(data.getTitle());
            response.setTag(data.getTag().stream()
                    .map(Tag::getName)
                    .findFirst()
                    .orElse(null));

            response.setCategory(data.getCategory().getName());
            response.setThumbnailUrl(data.getThumbnailUrl());

            listImageResponsDTOS.add(response);

        }
        return listImageResponsDTOS;

    }
}
