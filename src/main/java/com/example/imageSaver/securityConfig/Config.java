package com.example.imageSaver.securityConfig;

   import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.http.HttpMethod;


@Configuration
public class Config {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addContextCustomizers(context -> context.setAllowCasualMultipartParsing(true));
    }






    // TODO: check is user agent is firefox or postman, based on that show form login or api
    // TODO: addCaluse
    // TODO: pagination
    // TODO: decide image deletion only ADMIN
    // TODO : Database migration --use tools like---flyway or liqiudase
    // TODO : avoid using syso  ---logging framework---logback
//                .httpBasic(Customizer.withDefaults())








}
