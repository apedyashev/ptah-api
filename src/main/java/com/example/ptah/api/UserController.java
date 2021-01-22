package com.example.ptah.api;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import com.example.ptah.model.User;
import com.example.ptah.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;

@RestController
@RequestMapping("/api/users")
public class UserController {
    public final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @NonNull @RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public Optional<User> getById(@PathVariable("id") Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/me")
    public Optional<User> getCurrentUser(Principal principal) {
        return userService.findByUsername(principal.getName());
    }
}
