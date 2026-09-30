package com.example.imageSaver.controllers;


import com.example.imageSaver.dto.*;
import com.example.imageSaver.models.User;
import com.example.imageSaver.service.AuthService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRegisterLoginController {

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


    //update
    @PutMapping("/update")
    public ResponseEntity<UserUpdateResponseDTO> updateUser(@RequestBody  UpdateUserRequestDTO user ,   @AuthenticationPrincipal UserDetails currentUserDetails){
        UserUpdateResponseDTO responseDTO=authService.updateUser(currentUserDetails.getUsername() ,user);
        return ResponseEntity.ok(responseDTO);

    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(   @AuthenticationPrincipal UserDetails currentUserDetails){
         authService.deleteUser(currentUserDetails.getUsername() );
        return ResponseEntity.ok( "Deleted successfully");
    }









}
