package com.example.imageSaver.service;

import com.example.imageSaver.models.Role;
import com.example.imageSaver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    public RoleRepository roleRepository;

    public  void saveRole(Role role){
        roleRepository.save(role);
    };

}
