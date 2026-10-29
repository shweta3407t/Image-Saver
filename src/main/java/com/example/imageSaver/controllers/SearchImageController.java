package com.example.imageSaver.controllers;

import com.example.imageSaver.dto.PaginatedResponseDTO;
import com.example.imageSaver.dto.UploadImageResponseDTO;
import com.example.imageSaver.service.UploadImageService;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image/search")
@CrossOrigin(origins = "*")
public class SearchImageController {


    @Autowired
    public UploadImageService uploadImageService;

    //search
    @GetMapping("/keyword")
    public ResponseEntity<PaginatedResponseDTO> searchImagesByKeyword(
            @RequestParam("keyword" ) String keyword ,
            @RequestParam(value = "page"  ) int page,
            @RequestParam(value = "size"  ) int size
            ) {

        Pageable pageable=PageRequest.of( page , size);

        System.out.println("search start ");
         PaginatedResponseDTO  results = uploadImageService.searchImagesByKeyWord(keyword , pageable);
        System.out.println("serach end");
         return ResponseEntity.ok(results);


     }


    @GetMapping
    public ResponseEntity<PaginatedResponseDTO > searchAllImage(
            @RequestParam(value = "page" ) int page,
            @RequestParam(value = "size" ) int size
    ) {

        Pageable pageable=PageRequest.of( page , size  , Sort.by("createdAt").descending());

        PaginatedResponseDTO  results = uploadImageService.searchAllImage( pageable);
        return ResponseEntity.ok(results);
    }

}

