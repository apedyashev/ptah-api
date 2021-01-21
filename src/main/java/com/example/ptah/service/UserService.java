package com.example.ptah.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.ptah.dao.UserDao;
import com.example.ptah.model.ConfirmationToken;
import com.example.ptah.model.User;
import com.example.ptah.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final UserRepository userRepository;
    // private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    // @Autowired
    // UserService(@Qualifier("dbUserDao") UserDao userDao, BCryptPasswordEncoder
    // passwordEncoder) {
    // this.userDao = userDao;
    // this.passwordEncoder = passwordEncoder;
    // }

    public User create(User user) {
        return userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        // TODO: Send confirmation token
        ConfirmationToken token = new ConfirmationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setCreatedAt(LocalDateTime.now());
        // TODO: expiration time from config
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        token.setUser(user);

        confirmationTokenService.saveToken(token);

        return token.getToken();
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
