package com.example.ptah.user;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidationService implements Predicate<String> {
    @Override
    public boolean test(String email) {
        return true;
    }
}
