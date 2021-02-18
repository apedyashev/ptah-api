package com.example.ptah.auth;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.example.ptah.auth.dto.RegistrationRequest;
import com.example.ptah.auth.dto.UserRole;
import com.example.ptah.email.EmailSender;
import com.example.ptah.exception.ValidationException;
import com.example.ptah.jwt.JwtAuthenticationService;
import com.example.ptah.user.User;
import com.example.ptah.user.EmailValidationService;
import com.example.ptah.user.UserService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private static String TOKEN_FIELD_NAME = "token";
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidationService emailValidator;
    private final EmailSender emailSender;
    private final JwtAuthenticationService jwtAuthService;

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
        user.setEnabled(false);
        user.setRole(UserRole.USER);

        String token = userService.signUpUser(user);
        emailSender.send(request.getEmail(), buildEmail(request.getUsername(), token));
        return token;
    }

    public void confirmToken(String token, HttpServletResponse response) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new ValidationException(TOKEN_FIELD_NAME, "token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ValidationException(TOKEN_FIELD_NAME, "email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ValidationException(TOKEN_FIELD_NAME, "token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());

        User user = confirmationToken.getUser();
        jwtAuthService.authenticate(user, response);
        // try {
        // Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
        // user.getAuthorities());
        // SecurityContextHolder.getContext().setAuthentication(auth);
        // System.out.println(auth.getName());
        // } catch (Exception e) {
        // System.out.println(e.getMessage());

        // }
    }

    private String buildEmail(String name, String link) {
        return "Hi " + name + ",</br>" + "please confirm your registration with this code: " + link;
    }

}
