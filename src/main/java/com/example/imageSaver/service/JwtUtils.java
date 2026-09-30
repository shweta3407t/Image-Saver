package com.example.imageSaver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    private final String SECRET_KEY = "your-very-secure-and-long-secret-key-here";
    // 1. Generate the signing key from your plain text secret
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    // 2. Extract ALL claims (the entire payload)
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Verifies signature
                .build()
                .parseSignedClaims(token)    // Parses the token
                .getPayload();               // Retrieves the claims map
    }

    // 3. Extract standard "Subject" claim (usually username/email)
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }


    public  String generateToken(String username){
        Map<String , Object> claims =new HashMap<>();
        claims.put("userId", "xyz");
        claims.put("userEmail", "xyz@email.com");

        return Jwts.builder( )
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +  60 * 60 * 30 * 1000))
                .and()
                .signWith(getSigningKey())
                .compact() ;
    }


    public boolean validateToken(String token, UserDetails userDetail) {
        final  String  userName=extractUsername(token);
        return (userName.equals(userDetail.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String  token){
        return  extractClaim(token ,  Claims :: getExpiration);
    }

    public   <T>  T extractClaim(String token , Function<Claims , T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
}
