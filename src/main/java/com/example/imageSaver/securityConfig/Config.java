package com.example.imageSaver.securityConfig;

import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addContextCustomizers(context -> context.setAllowCasualMultipartParsing(true));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }



//    @Bean
//    public AuthenticationProvider authenticationProvider(HttpSecurity httpSecurity
//            , DaoAuthenticationProvider daoAuthenticationProvider ,
//            JwtAuthenticationConverter jwtAuthenticationConverter){
//
//
//    }



    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider authenticationProvider){
        return  new ProviderManager(authenticationProvider);
    }




}
