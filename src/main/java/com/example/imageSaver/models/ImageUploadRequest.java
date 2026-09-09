package com.example.imageSaver.models;

import org.springframework.web.multipart.MultipartFile;


public class ImageUploadRequest {

    private String title;
    private String description;

    private Category category;
    private Tag tag;

    private MultipartFile files;

    public  ImageUploadRequest(){}

    public ImageUploadRequest(String title, String description, Category category, Tag tag, MultipartFile files) {

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public MultipartFile getFiles() {
        return files;
    }

    public void setFiles(MultipartFile files) {
        this.files = files;
    }
}
