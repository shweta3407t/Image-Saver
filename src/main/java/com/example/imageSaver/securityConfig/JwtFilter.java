package com.example.imageSaver.securityConfig;


import com.example.imageSaver.models.CustomUserDetails;
import com.example.imageSaver.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader=request.getHeader("Authentication");
        String token=null;
        String username= null;
         if(authHeader != null && authHeader.startsWith("Bearer ")){
             token =authHeader.substring(7 );
             username=authService.extractUserName(token);
         }

         if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
             CustomUserDetails userDetail = context.getBean(CustomUserDetails.class);

             if(authService.validateToken(token , userDetail));
         }


    }
}
