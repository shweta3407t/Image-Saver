package com.example.imageSaver.dto;

import org.springframework.web.multipart.MultipartFile;

import java.nio.Buffer;


public class BulkImageUploadRequestDTO {

    private String title;
    private String description;

    private String category;
    private String tag;

    private Buffer imageBuffer;
//    private MultipartFile[] files;
}
