package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.UpdateRequestDTO;
import com.example.imageSaver.service.UpdateImageService;
import com.example.imageSaver.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ImageUpdateController {
    @Autowired
    public UpdateImageService updateImageService;




    //update
    @PutMapping("update")
    public  ResponseEntity<String> updateStudent(@RequestParam UpdateRequestDTO updateRequestDTO){
        updateImageService.updateImage(updateRequestDTO );

        return  ResponseEntity.ok("image updated successfully" ) ;
    }



    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@RequestParam String titleName){
        updateImageService.deleteImage(  titleName);
        return  ResponseEntity.ok("Image deleted");
    }










}
