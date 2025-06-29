package com.northEthio.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;  // Correct import
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date; // Correct import
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class JwtProvider {
    SecretKey key = Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes());
//SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Auto-generates a 256-bit key
//SecretKey key = Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication auth) {

        Collection<?extends GrantedAuthority> authorities=auth.getAuthorities();
        String roles=populateAuthorities(authorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();

//        return null;
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths=new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}
