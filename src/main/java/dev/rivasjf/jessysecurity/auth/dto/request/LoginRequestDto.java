package dev.rivasjf.jessysecurity.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto (
        @NotBlank(message = "Username cannot be empty")
        @Email(message = "Email must to be valid")
        String email,
        @NotBlank(message = "PublicKey cannot be empty")
        String publicKey
){
        public LoginRequestDto {
                if (email != null) email = email.trim();
                if (publicKey != null) publicKey = publicKey.trim();
        }
}
