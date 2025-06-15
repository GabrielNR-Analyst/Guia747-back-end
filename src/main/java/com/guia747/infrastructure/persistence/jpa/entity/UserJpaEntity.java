package com.guia747.infrastructure.persistence.jpa.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

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
@Setter
@Getter
public class UserJpaEntity extends JpaAuditableEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureUrl;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "provider_id")
    private String providerId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            indexes = {
                    @Index(name = "idx_user_roles_user", columnList = "user_id"),
                    @Index(name = "idx_user_roles_role", columnList = "role_id")
            }
    )
    private Set<RoleJpaEntity> roles = new HashSet<>();
}
