package com.example.imageSaver.controllers;

import com.example.imageSaver.models.Role;
import com.example.imageSaver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RoleController {

    @Autowired
    public RoleService roleService;


    @PostMapping("/role")
    public ResponseEntity<String> createRole(@RequestBody Role role){
        roleService.saveRole(role);
        return ResponseEntity.ok("role saved successfully");
    }



}
