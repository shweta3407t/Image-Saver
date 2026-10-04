package com.example.imageSaver.dto;

import java.time.LocalDateTime;

public class UploadImageResponseDTO {

    private Long id;
    private String title;
    private String tag;
    private String category;
    private String thumbnailUrl;
    private String description;

    private LocalDateTime createdAt;

    private  LocalDateTime updatedAt;

    public UploadImageResponseDTO(){}



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UploadImageResponseDTO(Long id, String title, String tag, String category, String thumbnailUrl, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
