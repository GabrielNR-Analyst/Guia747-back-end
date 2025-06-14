package com.guia747.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "user_accounts",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_user_accounts_email", columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_user_accounts_email", columnList = "email"),
                @Index(name = "idx_user_accounts_provider_id", columnList = "provider_id")
        }
)
public class UserJpaEntity extends JpaAuditableEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureUrl;

    @Column(name = "provider_name", length = 255)
    private String providerName;

    @Column(name = "provider_id", length = 255)
    private String providerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
