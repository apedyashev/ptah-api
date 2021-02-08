package com.example.ptah.auth;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.example.ptah.auth.dto.RegistrationRequest;
import com.example.ptah.auth.dto.UserRole;
import com.example.ptah.email.EmailSender;
import com.example.ptah.user.User;
import com.example.ptah.user.EmailValidationService;
import com.example.ptah.user.UserService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidationService emailValidator;
    private final EmailSender emailSender;

    @Transactional
    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if (!isEmailValid) {
            throw new IllegalStateException("email not valid");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setEnabled(true); // TODO:
        user.setRole(UserRole.USER);

        String token = userService.signUpUser(user);
        String link = "http://localhost:8090/api/v1/auth/confirm?token=" + token;
        emailSender.send(request.getEmail(), buildEmail(request.getUsername(), link));
        return token;
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());

        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "Hi " + name + ",</br>" + "please confirm your registration <a href=\"" + link + "\">" + link + "</a>";
    }
}
