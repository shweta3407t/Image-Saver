package com.example.imageSaver.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ImageUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private  String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "image_tag" ,
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tag = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY) // LAZY fetching prevents unnecessary database joins
    @JoinColumn(name = "category_id" ) // Defines foreign key column
    private Category category;

    private String thumbnailUrl;
    private String imageUrl;

    public ImageUpload(){}

    public ImageUpload(String title, String description, Set<Tag> tag, Category category, String thumbnailUrl, String imageUrl) {
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tag> getTag() {
        return tag;
    }

    public void setTag(Set<Tag> tag) {
        this.tag = tag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

