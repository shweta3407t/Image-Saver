package com.example.imageSaver.repository;

import com.example.imageSaver.models.ImageMetaData;
import com.example.imageSaver.models.ListImageResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageFileUploadRepository extends JpaRepository<ImageMetaData, Long> {
      Optional<List<ImageMetaData>> findAllByTitleIgnoreCase(String title);


    Optional<List<ImageMetaData>> findAllByTagId(Long tagId);

    Optional<List<ImageMetaData>> findAllByCategoryId(Long categoryId);





}
