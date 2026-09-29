package com.example.imageSaver.service;

import com.example.imageSaver.dto.LoginRequestDTO;
import com.example.imageSaver.dto.LoginResponseDTO;
import com.example.imageSaver.dto.RegisterRequestDTO;
import com.example.imageSaver.dto.RegisterResponseDTO;
import com.example.imageSaver.exception.IncorrectCredentialsException;
import com.example.imageSaver.exception.ResourceNotFoundException;
import com.example.imageSaver.models.Role;
import com.example.imageSaver.models.User;
import com.example.imageSaver.repository.RoleRepository;
import com.example.imageSaver.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public  JwtService jwtService;


    public RegisterResponseDTO register (RegisterRequestDTO userRegisterRequestDTO){
        User user = new User();
        user.setUserName(userRegisterRequestDTO.getUserName());
        String encodedPassword=passwordEncoder.encode(userRegisterRequestDTO.getPassword());


        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role=roleRepository.findByName("USER_ROLE").orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        user.getRoles().add(role);

        userRepository.save(user);


        RegisterResponseDTO responseDTO =new RegisterResponseDTO();

        responseDTO.setUserName(user.getUserName());
        responseDTO.setMassage("user Registered successfully");

        return  responseDTO;


    }

    public LoginResponseDTO verify(LoginRequestDTO requestDTO) throws BadRequestException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUserName(), requestDTO.getPassword()));


        //response

        if(authentication.isAuthenticated()){
            String token =jwtService.generateToken(requestDTO.getUserName());
            LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
            loginResponseDTO.setAccessToken(token);
             return   loginResponseDTO;
        }
            throw new IncorrectCredentialsException();

    }



    public String  extractUserName(String token){
        return  null;
    }
}
