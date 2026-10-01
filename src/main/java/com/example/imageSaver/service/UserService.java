package com.example.imageSaver.service;

import com.example.imageSaver.dto.UpdateUserRequestDTO;
import com.example.imageSaver.dto.UserUpdateResponseDTO;
import com.example.imageSaver.exception.ResourceNotFoundException;
import com.example.imageSaver.models.Role;
import com.example.imageSaver.models.User;
import com.example.imageSaver.repository.RoleRepository;
import com.example.imageSaver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder  ;

    @Autowired
    public RoleRepository roleRepository;

    @Transactional
    public UserUpdateResponseDTO updateUser(String currentUserName  , UpdateUserRequestDTO userRequestDTO){

        User currentUser =  userRepository.findByUserName(currentUserName).orElseThrow( () -> new UsernameNotFoundException("USer not found with this name"));

        currentUser.setUserName(userRequestDTO.getName());
        String encodedPassword=passwordEncoder.encode(userRequestDTO.getPassword());


        currentUser.setPassword(encodedPassword);
        currentUser.setEnabled(true);
        Role role=roleRepository.findByName("USER_ROLE").orElseThrow(() -> new ResourceNotFoundException("USER NOT FOUND"));
        currentUser.getRoles().add(role);

        userRepository.save(currentUser);



        //response
        UserUpdateResponseDTO responseDTO=new UserUpdateResponseDTO();
        responseDTO.setUserName(userRequestDTO.getName());
        responseDTO.setMassage(" Successfully User updated ");

        return responseDTO;
    }

    @Transactional
    public  void   deleteUser(String userName){
        userRepository.deleteByUserName(userName);
    }
}
