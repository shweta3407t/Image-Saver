package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.UpdateRequestDTO;
import com.example.imageSaver.service.UpdateImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UpdateImageController {
    @Autowired
    public UpdateImageService updateImageService;


    //update
    @PutMapping("update")
    public ResponseEntity<String> updateStudent(@RequestParam("id") Long id,
                                                @RequestParam("title") String title,
                                                @RequestParam("description") String description,
                                                @RequestParam("tag") String tag,
                                                @RequestParam("category") String category

    ) {

        UpdateRequestDTO updateRequestDTO = new UpdateRequestDTO(id, title, tag, category, description);

        updateImageService.updateImage(updateRequestDTO);

        return ResponseEntity.ok("image updated successfully");
    }


    @DeleteMapping("delete")
    public ResponseEntity<String> deleteImage(@RequestParam Long id) {
        updateImageService.deleteImage(id);
        return ResponseEntity.ok("Image deleted");
    }


}
