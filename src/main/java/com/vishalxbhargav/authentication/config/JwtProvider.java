package com.vishalxbhargav.authentication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;


import javax.crypto.SecretKey;
import java.util.Date;


public class JwtProvider {
    private static final SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECTET_KEY.getBytes());

    public static String generateToken(Authentication auth){
        return Jwts.builder()
                .setIssuer("vishalxbhargav")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .signWith(key)
                .compact();
    }
    public static String getEmailFromJwtToken(String jwt) {
        //Bearer token
        jwt=jwt.substring(7);
        Claims claims= Jwts.parserBuilder()
                .setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }
}
