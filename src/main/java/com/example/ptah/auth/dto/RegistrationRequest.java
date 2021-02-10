package com.example.ptah.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.ptah.validators.FieldMatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(field = "password", fieldMatch = "passwordConfirmation", message = "Passwords do not match!") })
public class RegistrationRequest {
    // More here:
    // https://javaee.github.io/javaee-spec/javadocs/javax/validation/constraints/package-summary.html
    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 5, max = 15)
    private String username;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @NotBlank
    private String passwordConfirmation;
}
