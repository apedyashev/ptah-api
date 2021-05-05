package com.example.ptah.user;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import com.example.ptah.exception.NotFoundException;

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
@RequestMapping("/api/v1/users")
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
    public User getById(@PathVariable("id") Long userId) {
        return userService.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @GetMapping("/me")
    public User getCurrentUser(Principal principal) {
        return userService.findByEmail(principal.getName()).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
