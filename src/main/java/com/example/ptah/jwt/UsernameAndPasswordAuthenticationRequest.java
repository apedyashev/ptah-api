package com.example.ptah.jwt;

import lombok.Data;

@Data
public class UsernameAndPasswordAuthenticationRequest {
    private String email;
    private String password;
}
