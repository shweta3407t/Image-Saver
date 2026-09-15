package com.example.imageSaver.repository;

import com.example.imageSaver.models.ListImageResponse;
import com.example.imageSaver.models.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagModelRepository extends JpaRepository<Tag,Long> {
     Optional<Tag> findByName(String name);

    Boolean existsByName(String tagName);
}
