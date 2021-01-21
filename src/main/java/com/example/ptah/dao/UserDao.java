package com.example.ptah.dao;

import java.util.Optional;
import java.util.UUID;

import com.example.ptah.model.User;

public interface UserDao {
    Optional<User> findByEmail(String email);

    User insert(User user);

    User findById(UUID id);

    User updateById(UUID id, User user);
}
