package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaUserAccountEntity;

public interface JpaUserAccountRepository extends JpaRepository<JpaUserAccountEntity, UUID> {

    Optional<JpaUserAccountEntity> findByEmail(String email);

    Optional<JpaUserAccountEntity> findByGoogleId(String googleId);
}
