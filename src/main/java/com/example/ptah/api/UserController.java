package com.example.ptah.api;

import javax.validation.Valid;

import com.example.ptah.model.User;
import com.example.ptah.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
}
