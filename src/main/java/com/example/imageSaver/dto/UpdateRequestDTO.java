package com.example.imageSaver.dto;

public class  UpdateRequestDTO {
    private Long id;
    private String title;
    private String tag;
    private String category;
    private String description;

    public UpdateRequestDTO(Long id, String title, String tag, String category, String description) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.category = category;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
