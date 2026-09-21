package com.example.imageSaver.controllers;

import com.example.imageSaver.dto.ListImageResponseDTO;
import com.example.imageSaver.models.ListImageResponse;
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
public class ImageSearchController {

    @Autowired
    public UploadImageService uploadImageService;

    @GetMapping("searchByTitle")
    public ResponseEntity<List<ListImageResponse>> searchImagesByTitle(@RequestParam("title") String titleName) {
        List<ListImageResponse> results = uploadImageService.searchImagesByTitle(titleName);
        return ResponseEntity.ok(results);
    }

    @GetMapping("searchByTag")
    public ResponseEntity<List<ListImageResponse>> searchImagesByTag(@RequestParam("tag") String tagName) {
        List<ListImageResponse> results = uploadImageService.searchImagesByTag(tagName);
        return ResponseEntity.ok(results);
    }

    @GetMapping("searchByCategory")
    public ResponseEntity<List<ListImageResponse>> searchImagesByCategory(@RequestParam("category") String categoryName) {
        List<ListImageResponse> results = uploadImageService.searchImagesByCategory(categoryName);
        return ResponseEntity.ok(results);
    }







}
