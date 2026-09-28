package com.example.imageSaver.service;

import com.example.imageSaver.dto.LoginRequestDTO;
import com.example.imageSaver.dto.LoginResponseDTO;
import com.example. imageSaver.dto.UserRegisterRequestDTO;
import com.example.imageSaver.dto.UserRegisterResponseDTO;
import com.example.imageSaver.exception.exceeption.ResourceNotFoundException;
import com.example.imageSaver.models.Role;
import com.example.imageSaver.models.User;
import com.example.imageSaver.repository.RoleRepository;
import com.example.imageSaver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    public  UserRepository userRepository;

    @Autowired
    public  RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder  ;

    @Autowired
    public AuthenticationManager authenticationManager;


    public UserRegisterResponseDTO register (UserRegisterRequestDTO  userRegisterRequestDTO){
        User user = new User();
        user.setUserName(userRegisterRequestDTO.getUserName());
        String encodedPassword=passwordEncoder.encode(userRegisterRequestDTO.getPassword());


        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role=roleRepository.findByName("USER_ROLE").orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        user.getRoles().add(role);

        userRepository.save(user);


        UserRegisterResponseDTO responseDTO =new UserRegisterResponseDTO();

        responseDTO.setUserName(user.getUserName());
        responseDTO.setMassage("user Registered successfully");

        return  responseDTO;


    }

    public String verify(LoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUserName(), requestDTO.getPassword()));


         if(authentication.isAuthenticated()){
             return  "Login successfully";
         }


        return  "login fail";
    }



    public String  extractUserName(String token){
        return  null;
    }
}
