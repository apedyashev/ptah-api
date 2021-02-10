package com.example.ptah.exception;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

// Note
// Another approach: annotations: https://wkrzywiec.medium.com/how-to-check-if-user-exist-in-database-using-hibernate-validator-eab110429a6#5791

@Getter
public class ValidationException extends RuntimeException {
    private String message = "Validation failed";
    private Map<String, Set<String>> errors;

    public ValidationException(String fieldName, String error) {
        super();
        this.errors = new HashMap<>();
        Set<String> errorDetails = new HashSet<>();
        errorDetails.add(error);
        this.errors.put(fieldName, errorDetails);
    }

    public ValidationError asObject() {
        return new ValidationError(message, errors);
    }

}
