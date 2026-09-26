package com.example.imageSaver.repository;

import com.example.imageSaver.dto.UploadImageResponseDTO;
import com.example.imageSaver.models.ImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageFileUploadRepository extends JpaRepository<ImageMetaData, Long> {
    Optional<List<ImageMetaData>> findAllByTitleIgnoreCase(String title);

    Optional<List<ImageMetaData>> findAllByTagId(Long tagId);

    Optional<List<ImageMetaData>> findAllByCategoryId(Long categoryId);

    Boolean  existsByTitle  (String title);

    //TODO:understand
    @Query("""
            SELECT DISTINCT new com.example.imageSaver.dto.UploadImageResponseDTO(
                p.id,\s
                p.title,\s
                t.name,\s
                c.name,\s
                p.thumbnailUrl,\s
                p.description
            )\s
            FROM ImageMetaData p\s
            LEFT JOIN p.tag t\s
            LEFT JOIN p.category c\s
            WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))\s
               OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))\s
               OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))\s
               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            
            """)
    List<UploadImageResponseDTO> searchImage(String keyword);


}
