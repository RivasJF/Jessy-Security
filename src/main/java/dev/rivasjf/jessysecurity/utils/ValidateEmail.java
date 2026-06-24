package dev.rivasjf.jessysecurity.utils;

import java.util.Objects;

public class ValidateEmail {
    public static String validateEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");
        email = email.trim();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if ( !email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        return email;
    }
}
