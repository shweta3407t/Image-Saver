package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.*;
import com.example.imageSaver.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRegisterLoginController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO userRegisterRequestDTO){

        RegisterResponseDTO userRegisterResponseDTO=
                authService.register(userRegisterRequestDTO);

        return  ResponseEntity.ok(userRegisterResponseDTO);

    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO ) throws BadRequestException {

        LoginResponseDTO response= authService.verify(requestDTO);

        return ResponseEntity.ok(response);
    }







}
