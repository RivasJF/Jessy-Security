package dev.rivasjf.jessysecurity.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDto (
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be empty")
        String publicKey,

        @NotBlank(message = "Public salt cannot be empty")
        String publicSalt


) {
        public UserRegisterRequestDto {
                if (username != null) username = username.trim();
                if (email != null) email = email.trim();
                if (publicKey != null) publicKey = publicKey.trim();
                if (publicSalt != null) publicSalt = publicSalt.trim();
        }
}