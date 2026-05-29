package dev.rivasjf.expensemanager.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Entity
@Table(name = "notices")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message", length = 300, nullable = false)
    private String message;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    protected Notice() {}

    private Notice(String message) {
        this.message = message;
    }

    public static Notice create(String message) {
        message = validMessage(message);
        return new Notice(message);
    }

    public void updateMessage(String message) {
        message = validMessage(message);
        this.message = message;
    }

    private static String validMessage(String message) {
        Objects.requireNonNull(message, "Message cannot be null");
        message = message.trim();
        if (message.length() > 300) {
            throw new IllegalArgumentException("Message cannot be longer than 255 characters");
        }
        return message;
    }

    public Long getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public String getCreatedAtUTC() {
        OffsetDateTime createdAt = this.getCreatedAt();
        return createdAt.withOffsetSameInstant(ZoneOffset.UTC).toString();
    }
}
