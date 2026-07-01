package dev.rivasjf.jessysecurity.account.entitie;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "account_additional_information")
public class AdditionalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Account account;
    @Enumerated(EnumType.STRING)
    @Column(name = "information_type", nullable = false)
    private AdditionalInformationType type;
    @Column(name = "information_value",  nullable = false)
    private String value;

    protected AdditionalInformation() {}

    private AdditionalInformation(Account account, AdditionalInformationType type, String value) {
        this.account = account;
        this.type = type;
        this.value = value;
    }

    public static AdditionalInformation create(Account account, AdditionalInformationType type, String value) {
        return new AdditionalInformation(account, type, value);
    }

    public Long getId() {
        return id;
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
}
