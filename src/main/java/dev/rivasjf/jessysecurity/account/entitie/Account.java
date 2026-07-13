package dev.rivasjf.jessysecurity.account.entitie;

import dev.rivasjf.jessysecurity.user.entity.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(name = "public_id", nullable = false, updatable = false)
    private UUID publicId;
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

    @PrePersist
    private void prePersist() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID();
        }
    }

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

    public static Account create(
            User user,
            String title,
            String username,
            String description,
            CategoryAccount category) {
        return new Account(user, title, username, description, category, null);
    }

    public void addAdditionalInformation(List<AdditionalInformation> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Long getId() {
        return id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public List<AdditionalInformation> getAdditionalInformation() {
        return additionalInformation;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public CategoryAccount getCategory() {
        return category;
    }
}
