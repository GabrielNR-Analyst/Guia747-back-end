package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.PermissionJpaEntity;

public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, UUID> {

    Optional<PermissionJpaEntity> findByName(String name);
}
