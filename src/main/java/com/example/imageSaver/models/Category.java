package com.example.imageSaver.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public   long id;

    public   String name ;

    public Category(String name) {
        this.name = name;
    }



    public  Category(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
