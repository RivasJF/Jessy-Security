package dev.rivasjf.jessysecurity.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDto (
        @NotEmpty(message = "Username cannot be empty")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
        String password
) {
}