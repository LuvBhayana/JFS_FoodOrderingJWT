package com.food.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {
    private SecretKey key;

    public JwtProvider() {
        try {
            // Generate a secure key
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            // Alternatively, if you have a securely stored key, you can retrieve and decode it:
            // String base64Key = "your-base64-encoded-secret-key";
            // key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));

        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize JwtProvider: " + e.getMessage(), e);
        }
    }

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        return jwt;
    }

    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}
