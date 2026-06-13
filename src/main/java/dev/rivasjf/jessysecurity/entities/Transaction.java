package dev.rivasjf.jessysecurity.entities;

import dev.rivasjf.jessysecurity.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity @Table(name = "transactions")
public class Transaction {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;
    @Column(name = "amount", precision = 8 , scale = 2, nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_category", nullable = false)
    private TransactionCategory category;
    @Column(name = "description", length = 100)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    private TransactionType type;
    @CreationTimestamp
    @Column(name = "created_at",  updatable = false, nullable = false)
    private OffsetDateTime  createdAt;

    protected Transaction() {}

    private Transaction(User user, BigDecimal amount, TransactionCategory category, String description, TransactionType type) {
        Objects.requireNonNull(user, "User must not be null");
        Objects.requireNonNull(category, "Category must not be null");
        this.user = user;
        this.category = category;
        this.amount = validAmount(amount, category);
        this.description = validDescription(description);
        this.type = type;
    }

    private BigDecimal validAmount(BigDecimal amount, TransactionCategory category) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (category.equals(TransactionCategory.EXPENSE) || category.equals(TransactionCategory.LENT)) {
            return amount.negate();
        }
        return amount;
    }

    private String validDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return null;
        }
        description = description.trim();
        if (description.length() > 100) {
            throw new IllegalArgumentException("Description must be less than 100 characters");
        }
        return description;
    }
}
