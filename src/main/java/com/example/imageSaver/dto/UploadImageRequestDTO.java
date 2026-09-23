package com.example.imageSaver.dto;

import org.springframework.web.multipart.MultipartFile;


public class UploadImageRequestDTO {

    private String title;
    private String description;

    private String category;
    private String tag;

    private MultipartFile files;

    public UploadImageRequestDTO(){}

    public UploadImageRequestDTO(String title, String description, String category, String tag, MultipartFile files) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.tag = tag;
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public MultipartFile getFiles() {
        return files;
    }

    public void setFiles(MultipartFile files) {
        this.files = files;
    }
}
