package com.example.imageSaver.repository;

import com.example.imageSaver.models.ListImageResponse;
import com.example.imageSaver.models.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagModelRepository extends JpaRepository<Tag,Long> {
    @EntityGraph(attributePaths = "tag")
    ListImageResponse findByName(String tagName);

    Boolean existsByName(String tag);
}
