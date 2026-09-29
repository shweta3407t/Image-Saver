package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.LoginRequestDTO;
import com.example.imageSaver.dto.LoginResponseDTO;
import com.example.imageSaver.dto.RegisterRequestDTO;
import com.example.imageSaver.dto.RegisterResponseDTO;
import com.example.imageSaver.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class RegisterLoginController {

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
