package dev.rivasjf.jessysecurity.account.entitie;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account_additional_information")
public class AdditionalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Account account;
    @Enumerated(EnumType.STRING)
    @Column(name = "information_type", nullable = false)
    private AdditionalInformationType type;
    @Column(name = "information_value",  nullable = false)
    private String value;
    @Column(name = "information_key", nullable = false)
    private String key;

    protected AdditionalInformation() {}

    @PrePersist
    private void prePersist() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID();
        }
    }

    private AdditionalInformation(Account account, AdditionalInformationType type, String value,  String key) {
        this.account = account;
        this.type = type;
        this.value = value;
        this.key = key;
    }

    public static AdditionalInformation create(Account account, AdditionalInformationType type, String value, String key) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        return new AdditionalInformation(account, type, value, key);
    }

    public void updateInformation(AdditionalInformationType type, String value, String key) {
        if (type != null) {
            this.type = type;
        }
        //cannot change value without key
        if (key != null && value != null) {
            this.value = value;
            this.key = key;
        }
        if (key != null && value == null) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }else {
            this.value = value;
        }
    }

    public Long getId() {
        return id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public String getValue() {
        return value;
    }

    public Account getAccount() {
        return account;
    }

    public AdditionalInformationType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }
}
