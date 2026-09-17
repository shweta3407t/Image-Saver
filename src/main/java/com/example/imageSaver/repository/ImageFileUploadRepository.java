package com.example.imageSaver.repository;

import com.example.imageSaver.models.ImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageFileUploadRepository extends JpaRepository<ImageMetaData, Long> {
      Optional<ImageMetaData> findByTitle(String title);
}
