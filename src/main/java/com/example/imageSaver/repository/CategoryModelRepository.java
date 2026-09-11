package com.example.imageSaver.repository;

import com.example.imageSaver.models.Category;
import com.example.imageSaver.models.ListImageResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryModelRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = "category")
    ListImageResponse findByName(String categoryName);

}
