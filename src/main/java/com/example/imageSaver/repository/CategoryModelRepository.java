package com.example.imageSaver.repository;

import com.example.imageSaver.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryModelRepository extends JpaRepository<Category, Long> {
}
