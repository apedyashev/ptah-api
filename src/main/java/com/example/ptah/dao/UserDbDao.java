package com.example.ptah.dao;

import java.util.Optional;
import java.util.UUID;

import com.example.ptah.model.User;
import com.example.ptah.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("dbUserDao")
public class UserDbDao implements UserDao {
    private final UserRepository userRepository;

    @Autowired
    UserDbDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User insert(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        // user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return new User();
    }

    @Override
    public User findById(UUID id) {
        return new User();
    }

    @Override
    public User updateById(UUID id, User user) {
        return new User();
    }
}
