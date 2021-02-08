package com.example.ptah.auth;

import javax.validation.Valid;

import com.example.ptah.auth.dto.ConfirmTokenRequest;
import com.example.ptah.auth.dto.RegistrationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@Valid @NonNull @RequestBody RegistrationRequest request) {
        authService.register(request);
    }

    @PostMapping("/confirm")
    public String confirm(@Valid @NonNull @RequestBody ConfirmTokenRequest request) {
        return authService.confirmToken(request.getToken());
    }

}
