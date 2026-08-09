package dev.rivasjf.jessysecurity.account.entitie;

import dev.rivasjf.jessysecurity.account.dto.AccountAdditionalInformationUpdateDto;
import dev.rivasjf.jessysecurity.account.dto.AccountUpdateDto;
import dev.rivasjf.jessysecurity.user.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    public void updateAccount(AccountUpdateDto dto) {
        this.updateInformation(dto.title(), dto.username(), dto.description(), dto.category());
        this.updateAdditionalInformation(dto.additionalInformation());
    }

    private void updateInformation(String title, String username, String description, CategoryAccount category) {
        if(title != null) {
            this.title = title;
        }
        if (username != null) {
            this.username = username;
        }
        if (description != null) {
            this.description = description;
        }
        if (category != null) {
            this.category = category;
        }
    }

    private void updateAdditionalInformation(List<AccountAdditionalInformationUpdateDto> additionalInformation) {
        additionalInformation.stream()
                .forEach(dto -> {
                    if (dto.id() == null || dto.id().isEmpty()) {
                        this.addAdditionalInformation(AdditionalInformation.create(this, dto.type(), dto.value(), dto.key()));
                    } else if (dto.deleted() != null && dto.deleted()) {
                        this.removeAdditionalInformation(dto.id());
                    } else if (dto.id() != null && !dto.id().isEmpty()) {
                        this.updateAdditionalInformation(dto);
                    }
                });
    }

    private void removeAdditionalInformation(String publicIdAdditionalInformation) {
        AdditionalInformation additionalInformation = this.additionalInformation.stream()
                .filter(info -> info.getPublicId().toString().equals(publicIdAdditionalInformation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Additional information not found in the account"));
        this.additionalInformation.remove(additionalInformation);
    }

    private void updateAdditionalInformation(AccountAdditionalInformationUpdateDto additionalInformationUpdateDto) {
        AdditionalInformation additionalInformation = this.additionalInformation.stream()
                .filter(info -> info.getPublicId().toString().equals(additionalInformationUpdateDto.id()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Additional information not found in the account"));
        additionalInformation.updateInformation(additionalInformationUpdateDto.type(), additionalInformationUpdateDto.value(), additionalInformationUpdateDto.key());
    }

    private void addAdditionalInformation (AdditionalInformation additionalInformation) {
        this.additionalInformation.add(additionalInformation);
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
