package com.example.imageSaver.controllers;

import com.example.imageSaver.repository.TagModelRepository;
import com.example.imageSaver.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    public TagService tagService;

    @PostMapping
    public ResponseEntity<String> saveTag(String tag){
        tagService.saveTag(tag);

        return  ResponseEntity.ok("Tag saved");

    }
}
