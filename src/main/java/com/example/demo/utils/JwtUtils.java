package com.example.demo.utils;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;


public class JwtUtils {

    private static final String SECRET_KEY = "your-secret-key";  // Store securely

    // Generate token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 5 * 5)) // 5 mins expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY) // Set the signing key here
                    .build()
                    .parseClaimsJws(token); // Parse the token
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get username from token
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token) // Parse the token and retrieve claims
                .getBody(); // Get the body of the JWT
        return claims.getSubject(); // Return the subject (username)
    }
}
