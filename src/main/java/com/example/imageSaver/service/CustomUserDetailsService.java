package com.example.imageSaver.service;

import com.example.imageSaver.models.CustomUserDetails;
import com.example.imageSaver.models.User;
import com.example.imageSaver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if(user == null){
            System.out.println("User not found");
            throw  new UsernameNotFoundException("user not found");
        }

        return  new CustomUserDetails(user);

    }
}
