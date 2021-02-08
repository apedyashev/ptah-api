package com.example.ptah.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "application.jwt")
@NoArgsConstructor
@Configuration
@Data
public class JwtConfig {
    private String secretKey;
    private Integer tokenExpirationDays;

    public SecretKey getEncodedSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getTokenPrefix() {
        return "Bearer ";
    }

}
