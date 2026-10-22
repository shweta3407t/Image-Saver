package com.example.imageSaver.repository;

import com.example.imageSaver.dto.PaginatedResponseDTO;
import com.example.imageSaver.dto.UploadImageResponseDTO;
import com.example.imageSaver.models.ImageMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageFileUploadRepository extends JpaRepository<ImageMetaData, Long> {

    boolean  existsByTitle  (String title);

    boolean existsById(Long id);

    //TODO:understand


    @Query("""
            SELECT DISTINCT p
            FROM ImageMetaData p\s
            LEFT JOIN p.tag t\s
            LEFT JOIN p.category c\s
            WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))\s
               OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))\s
               OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))\s
               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))

            """)
    Page<ImageMetaData> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


    Page<ImageMetaData> findAllByOrderByCreatedAtAsc(Pageable pageable);
}

