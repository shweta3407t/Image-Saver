package com.example.imageSaver.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToMany(mappedBy = "category")
    List<Category> name = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Category> getName() {
        return name;
    }

    public void setName(List<Category> name) {
        this.name = name;
    }
}
