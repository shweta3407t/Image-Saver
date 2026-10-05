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
@RequestMapping("/api/image/search")
public class SearchImageController {


    @Autowired
    public UploadImageService uploadImageService;

    //search
    @GetMapping("{keyword}")
    public ResponseEntity<PaginatedResponseDTO<UploadImageResponseDTO>> searchImagesByKeyword(
            @RequestParam("keyword" ) String keyword ,
            @RequestParam(value = "page" , defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "10") int size
            ) {

        Pageable pageable=PageRequest.of( page , size);



         PaginatedResponseDTO<UploadImageResponseDTO> results = uploadImageService.searchImagesByKeyWord(keyword , pageable);
         return ResponseEntity.ok(results);
    }


    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<UploadImageResponseDTO>> searchAllImage(
            @RequestParam(value = "currentPage" , defaultValue = "0") int page,
            @RequestParam(value = "itemLimit" , defaultValue = "5") int size
    ) {

        Pageable pageable=PageRequest.of( page , size);

        PaginatedResponseDTO<UploadImageResponseDTO> results = uploadImageService.searchAllImage( pageable);
        return ResponseEntity.ok(results);
    }

}
