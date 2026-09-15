package com.example.imageSaver.repository;

import com.example.imageSaver.models.ImageUpload;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageFileUploadRepository extends JpaRepository<ImageUpload, Long> {
      Optional<ImageUpload> findByTitle(String title);
}
