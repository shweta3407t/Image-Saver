package com.example.imageSaver.models;

public class ListImageResponse {
    long id;
    String title;
    String tag;
    String category;
    String thumbnailUrl;

    public ListImageResponse(){}

    public ListImageResponse( String title, String tag, String category, String thumbnailUrl) {

        this.title = title;
        this.tag = tag;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
