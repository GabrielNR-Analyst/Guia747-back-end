package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCategoryEntity;

public interface CategoryJpaRepository extends JpaRepository<JpaCategoryEntity, UUID> {

    boolean existsByNameEqualsIgnoreCase(String name);

    List<JpaCategoryEntity> findAllByIdIn(List<UUID> ids);
}
