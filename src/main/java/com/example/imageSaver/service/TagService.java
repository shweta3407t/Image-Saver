package com.example.imageSaver.service;

import com.example.imageSaver.repository.TagModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    public TagModelRepository tagModelRepository;

    public  void  saveTag(String tag){
         tagModelRepository.save(tag);
    }
}
