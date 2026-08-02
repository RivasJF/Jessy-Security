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
        return new AdditionalInformation(account, type, value, key);
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
