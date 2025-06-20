package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaStateEntity;

public interface JpaStateRepository extends JpaRepository<JpaStateEntity, UUID> {

    Optional<JpaStateEntity> findByUf(String uf);
}
