package com.example.ptah.exception;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {
    private String message;
    private Map<String, Set<String>> errors;
}
