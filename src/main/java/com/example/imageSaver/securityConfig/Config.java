package com.example.imageSaver.securityConfig;

 import com.example.imageSaver.models.CustomUserDetails;
 import com.example.imageSaver.service.CustomUserDetailsService;
   import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.http.HttpMethod;
 import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 import org.springframework.security.authorization.method.AuthorizeReturnObject;
 import org.springframework.security.config.Customizer;
 import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
   import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class Config {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addContextCustomizers(context -> context.setAllowCasualMultipartParsing(true));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity , AuthenticationProvider provider){

        return  httpSecurity.csrf(csrf -> csrf.disable())

                .authenticationProvider(provider)

                .authorizeHttpRequests(request ->
                        request.requestMatchers(  "/api/auth/register" , "/api/auth/login"  , "/api/role")
                                .permitAll()
                                 .anyRequest()
                                .authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationProvider provider(CustomUserDetailsService customUserDetailsService){

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(customUserDetailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return  provider;
    }

    @Bean
    public  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return  configuration.getAuthenticationManager();
    }



    // TODO: check is user agent is firefox or postman, based on that show form login or api
    // TODO: addCaluse
    // TODO: pagination
    // TODO: decide image deletion only ADMIN
    // TODO : Database migration --use tools like---flyway or liqiudase
    // TODO : avoid using syso  ---logging framework---logback
//                .httpBasic(Customizer.withDefaults())








}
