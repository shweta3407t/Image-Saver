package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.UpdateImageRequestDTO;
import com.example.imageSaver.service.UpdateImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class UpdateImageController {
    @Autowired
    public UpdateImageService updateImageService;


    //update
    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestParam("id") Long id,
                                                @RequestParam("title") String title,
                                                @RequestParam("description") String description,
                                                @RequestParam("tag") String tag,
                                                @RequestParam("category") String category

    ) {

        UpdateImageRequestDTO updateImageRequestDTO = new UpdateImageRequestDTO(id, title, tag, category, description);

        System.out.println("enter");
        String updateResponse = updateImageService.updateImage(updateImageRequestDTO);

        return ResponseEntity.ok(updateResponse);

     }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@RequestParam("id") Long id) {

        String deleteResponse= updateImageService.deleteImage(id);

        return  ResponseEntity.ok(deleteResponse);


    }


}
