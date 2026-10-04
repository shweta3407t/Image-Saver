package com.example.imageSaver.controllers;

import com.example.imageSaver.dto.PaginatedResponseDTO;
import com.example.imageSaver.dto.UploadImageResponseDTO;
import com.example.imageSaver.service.UploadImageService;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<PaginatedResponseDTO<UploadImageResponseDTO>> searchImagesByCategory(
            @RequestParam("keyword" ) String keyword ,
            @RequestParam(value = "page" , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
            ) {

        Pageable pageable=PageRequest.of( page , size);



         PaginatedResponseDTO<UploadImageResponseDTO> results = uploadImageService.searchImagesByKeyWord(keyword , pageable);
         return ResponseEntity.ok(results);
    }


    // q =  SELECT * FROM images WHERE title CONTAINS '%query%' OR description contains '%query%' ...

    // 1. get all records from below query.
    // 2. pages = totalRecord / limit

    // page = 1
    // limit = 10
    // offset = limit * page 1 * 10 = 10

    // p += "LIMIT = $limit OFFSET= $offset"



}
