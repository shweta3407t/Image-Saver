package com.example.imageSaver.securityConfig;


import com.example.imageSaver.service.CustomUserDetailsService;
import com.example.imageSaver.service.JwtService;
 import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
 import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    public ApplicationContext applicationContext;

    @Autowired
    private  JwtService jwtService;

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
             username=jwtService.extractUserName(token);
         }

         if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
             UserDetails userDetail=applicationContext.getBean(CustomUserDetailsService.class).loadUserByUsername(username);

             if(jwtService.validateToken(token , userDetail)){
                 UsernamePasswordAuthenticationToken authenticationToken=
                         new UsernamePasswordAuthenticationToken(
                                 userDetail , null , userDetail .getAuthorities());

                 authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                         .buildDetails(request));
             }
         }

         filterChain.doFilter(request , response);

    }
}
