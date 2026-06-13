package dev.rivasjf.jessysecurity.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity @Table(name = "transaction_types")
public class TransactionType {
    @Id
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(name = "name",length = 30, nullable = false)
    private String name;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    TransactionType() {}

    private TransactionType(String name) {
        this.name = name;
    }

    public static TransactionType create(String name) {
        return new TransactionType(name);
    }

    private String validType(String name) {
        Objects.requireNonNull(name);
        name = name.toUpperCase().trim();
        if (name.length() < 3 || name.length() > 30) {
            throw new IllegalArgumentException(String.format("Invalid name '%s'", name));
        }
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }
}
