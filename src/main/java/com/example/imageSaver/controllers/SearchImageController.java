package com.example.imageSaver.controllers;

import com.example.imageSaver.dto.UploadImageResponseDTO;
import com.example.imageSaver.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class SearchImageController {


    @Autowired
    public UploadImageService uploadImageService;

    //search
    @GetMapping("/search")
    public ResponseEntity<List<UploadImageResponseDTO>> searchImagesByCategory(@RequestParam("keyword") String keyword) {
         List<UploadImageResponseDTO> results = uploadImageService.searchImagesByKeyWord(keyword);
         return ResponseEntity.ok(results);
    }


}
