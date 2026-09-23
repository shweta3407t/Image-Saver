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
@RequestMapping
public class SearchImageController {

    @Autowired
    public UploadImageService uploadImageService;

    @GetMapping("searchByTitle")
    public ResponseEntity<List<UploadImageResponseDTO>> searchImagesByTitle(@RequestParam("title") String titleName) {
        List<UploadImageResponseDTO> results = uploadImageService.searchImagesByTitle(titleName);
        return ResponseEntity.ok(results);
    }

    @GetMapping("searchByTag")
    public ResponseEntity<List<UploadImageResponseDTO>> searchImagesByTag(@RequestParam("tag") String tagName) {
        List<UploadImageResponseDTO> results = uploadImageService.searchImagesByTag(tagName);
        return ResponseEntity.ok(results);
    }

    @GetMapping("searchByCategory")
    public ResponseEntity<List<UploadImageResponseDTO>> searchImagesByCategory(@RequestParam("category") String categoryName) {
        List<UploadImageResponseDTO> results = uploadImageService.searchImagesByCategory(categoryName);
        return ResponseEntity.ok(results);
    }







}
