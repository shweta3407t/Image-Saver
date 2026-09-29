package com.example.imageSaver.securityConfig;

 import com.example.imageSaver.models.CustomUserDetails;
 import com.example.imageSaver.service.CustomUserDetailsService;
 import com.example.imageSaver.service.JwtService;
  import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        return  httpSecurity.csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(request ->
                        request.requestMatchers(  "/register" , "/login" , "/role")
                                .permitAll()
                                .anyRequest()
                                .authenticated())

                // TODO: check is user agent is firefox or postman, based on that show form login or api
                // TODO: addCaluse
                .httpBasic(Customizer.withDefaults())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Bean
    public DaoAuthenticationProvider provider(){

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(customUserDetailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return  provider;
    }

    @Bean
    public  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return  configuration.getAuthenticationManager();
    }








}
