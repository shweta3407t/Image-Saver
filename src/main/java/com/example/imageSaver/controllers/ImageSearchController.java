package com.example.imageSaver.controllers;

import com.example.imageSaver.models.ListImageResponse;
import com.example.imageSaver.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class ImageSearchController {

    @Autowired
    public UploadImageService uploadImageService;



    @GetMapping("/searchByTitle")
    public ResponseEntity<ListImageResponse> searchImageByTitle(@RequestParam("title") String imageTitle) {

        ListImageResponse resource=uploadImageService.searchImageByTitle(imageTitle);
        return ResponseEntity.ok(resource);

    }



    @GetMapping("/searchByTag")
    public ResponseEntity<ListImageResponse> searchImageByTag(@RequestParam String tag){

        ListImageResponse response= uploadImageService.searchImageByTag(tag);

        return  ResponseEntity.ok(response);

    }
    @GetMapping("/searchByCategory")
    public ResponseEntity<ListImageResponse> searchImageByCategory(@RequestParam String category){

        ListImageResponse response=  uploadImageService.searchImageByCategory(category);

        return  ResponseEntity.ok(response);

    }

}
