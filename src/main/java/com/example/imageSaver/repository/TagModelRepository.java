package com.example.imageSaver.repository;

import com.example.imageSaver.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagModelRepository extends JpaRepository<Tag,Long> {
}
