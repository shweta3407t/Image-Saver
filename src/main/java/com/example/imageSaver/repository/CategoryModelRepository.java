package com.example.imageSaver.repository;

import com.example.imageSaver.models.Category;
import com.example.imageSaver.models.ListImageResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryModelRepository extends JpaRepository<Category, Long> {

     
     Optional<Category> findByName(String categoryName);

}
