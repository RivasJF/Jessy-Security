package dev.rivasjf.expensemanager.auth.services;

import dev.rivasjf.expensemanager.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private String secretKey = "3nEXMTCpNcpiUbA3q6U8MffjEKNhCocSAaSj4LBQwHKTAqcrXRV/E1cEKDFEEogQQCoPUp0seNslLKVnXkj2fA==";
    private Long expiration = 300000L;
    private Long refreshExpiration = 604800000L;


    public String generateToken(User user){
        return this.buildToken(user, expiration);
    }

    public String refreshToken(User user){
        return this.buildToken(user, refreshExpiration);
    }

    private String buildToken(User user, Long expiration){
        return Jwts.builder()
                .id(user.getId().toString())
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.getSingSecretKey())
                .compact();
    }

    public String extractUsername(String token){
        try {
            Claims jwtToken = Jwts.parser()
                    .verifyWith(this.getSingSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return jwtToken.getSubject();
        } catch (io.jsonwebtoken.JwtException e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, User user){
        String username = this.extractUsername(token);
        if (username == null) return false;
        return (username.equals(user.getEmail())) && !this.isTokenExpired(token);
    }
    public boolean isTokenValid(String token, UserDetails user){
        String username = this.extractUsername(token);
        if (username == null) return false;
        return (username.equals(user.getUsername())) && !this.isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        try {
            Claims jwtToken = Jwts.parser()
                    .verifyWith(this.getSingSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return jwtToken.getExpiration().before(new Date());
        } catch (io.jsonwebtoken.JwtException e) {
            return true;
        }
    }

    private SecretKey getSingSecretKey(){
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
