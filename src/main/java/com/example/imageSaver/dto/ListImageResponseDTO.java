package com.example.imageSaver.dto;

import com.example.imageSaver.models.ImageMetaData;
import com.example.imageSaver.models.ListImageResponse;
import com.example.imageSaver.models.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListImageResponseDTO {

    public List<ListImageResponse> convertToListImageResponse (List<ImageMetaData> imageMetaData){

        List<ListImageResponse> listImageResponses=new ArrayList<>();

        for(int i=0 ; i<imageMetaData.toArray().length  ; i++){
            ListImageResponse response=new ListImageResponse();

            ImageMetaData data=imageMetaData.get(i);

            response.setDescription(data.getDescription());
            response.setTitle(data.getTitle());
            response.setTag(data.getTag().stream()
                    .map(Tag::getName)
                    .findFirst()
                    .orElse(null));

            response.setCategory(data.getCategory().getName());
            response.setThumbnailUrl(data.getThumbnailUrl());

            listImageResponses.add(response);

        }


        return listImageResponses;

    }
}
