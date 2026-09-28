package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.LoginRequestDTO;
import com.example.imageSaver.dto.LoginResponseDTO;
import com.example.imageSaver.dto.UserRegisterRequestDTO;
import com.example.imageSaver.dto.UserRegisterResponseDTO;
import com.example.imageSaver.models.User;
import com.example.imageSaver.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RegisterLoginController {

    @Autowired
    private AuthService authService;




    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO){

        UserRegisterResponseDTO userRegisterResponseDTO=
                authService.register(userRegisterRequestDTO);

        return  ResponseEntity.ok(userRegisterResponseDTO);

    }


    @PostMapping("/login")
    public ResponseEntity<String> login(LoginRequestDTO requestDTO ){
        String response= authService.verify(requestDTO);
        return ResponseEntity.ok(response);
    }









}
