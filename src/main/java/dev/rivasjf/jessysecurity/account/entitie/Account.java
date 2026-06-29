package dev.rivasjf.jessysecurity.account.entitie;

import dev.rivasjf.jessysecurity.user.entity.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity()
@Table(name = "accounts")
public class Account {
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "username", length = 100, nullable = false)
    private String username;
    @Column(name = "description", length = 255)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CategoryAccount category;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdditionalInformation> additionalInformation;

    protected Account(){}

    private Account(
            User user,
            String title,
            String username,
            String description,
            CategoryAccount category,
            List<AdditionalInformation> additionalInformation) {
        this.user = user;
        this.title = title;
        this.username = username;
        this.description = description;
        this.category = category;
        this.additionalInformation = additionalInformation;
    }

}
