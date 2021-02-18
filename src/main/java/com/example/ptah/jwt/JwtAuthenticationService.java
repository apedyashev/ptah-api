package com.example.ptah.jwt;

import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.example.ptah.user.User;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtAuthenticationService {
    private final JwtConfig jwtConfig;

    public void authenticate(User user, HttpServletResponse response) {
        try {
            Authentication authResult = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authResult);
            System.out.println(authResult.getName());

            Integer tokenExpirationDays = jwtConfig.getTokenExpirationDays();
            String token = Jwts.builder()
                    // see jwt.io
                    .setSubject(authResult.getName())
                    // payload (see jwt.io)
                    .claim("authorities", authResult.getAuthorities()).setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(tokenExpirationDays)))
                    .signWith(jwtConfig.getEncodedSecretKey()).compact();

            response.addHeader("Authorization", jwtConfig.getTokenPrefix() + token);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
