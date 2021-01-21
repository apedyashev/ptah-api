package com.example.ptah.service;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidationService implements Predicate<String> {
    @Override
    public boolean test(String email) {
        return true;
    }
}
