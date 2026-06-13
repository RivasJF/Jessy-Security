package dev.rivasjf.jessysecurity.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;


@Getter
@Entity @Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private UUID id;
    @Column(name = "username", length = 30, nullable = false)
    private String username;
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    @Column(name = "password_hash",  length = 255,  nullable = false)
    private String passwordHash;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    protected User() {}

    private User(String username, String email, String password) {
        this.username = this.validUsername(username);
        this.email = this.validEmail(email);
        this.passwordHash = password;
    }

    public static User create(String username, String email, String password) {
        return new User(username, email, password);
    }

    public void changeUsername(String username) {
        this.username = this.validUsername(username);
    }

    private String validUsername(String username) {
        Objects.requireNonNull(username, "Username cannot be null");
        username = username.trim();
        if (username.length() < 3 || username.length() > 30) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters");
        }
        return username.trim();
    }

    private String validEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");
        email = email.trim();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if ( !email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        return email;
    }

    public static String validPassword(String password) {
        Objects.requireNonNull(password, "Password cannot be null");
        password = password.trim();
        if (password.length() < 6 || password.length() > 30) {
            throw new IllegalArgumentException("Password must be between 8 and 16 characters");
        }
        return password;
    }

}
