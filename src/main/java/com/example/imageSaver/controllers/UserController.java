package com.example.imageSaver.controllers;

import com.example.imageSaver.dto.*;
import com.example.imageSaver.service.AuthService;
import com.example.imageSaver.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;



    //update
    @PutMapping("/update")
    public ResponseEntity<UserUpdateResponseDTO> updateUser(@RequestBody  UpdateUserRequestDTO user , @AuthenticationPrincipal UserDetails currentUserDetails){
        UserUpdateResponseDTO responseDTO=userService.updateUser(currentUserDetails.getUsername() ,user);
        return ResponseEntity.ok(responseDTO);

    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(   @AuthenticationPrincipal UserDetails currentUserDetails){
        userService.deleteUser(currentUserDetails.getUsername() );
        return ResponseEntity.ok( "Deleted successfully");
    }

}
