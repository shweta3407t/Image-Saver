package com.example.imageSaver.models;

public class ListImageResponse {

    String title;
    String tag;
    String category;
    String thumbnailUrl;
    String description;

    public ListImageResponse(){}

    public ListImageResponse(String title, String tag, String category, String thumbnailUrl, String description) {
        this.title = title;
        this.tag = tag;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
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

    @Override
    public String toString() {
        return "ListImageResponse{" +
                "title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", category='" + category + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
