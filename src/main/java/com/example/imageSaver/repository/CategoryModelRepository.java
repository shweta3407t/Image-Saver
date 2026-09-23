package com.example.imageSaver.repository;

import com.example.imageSaver.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryModelRepository extends JpaRepository<Category, Long> {

     
     Optional<Category> findByNameIgnoreCase(String categoryName);

     Boolean existsByName(String name);


}
