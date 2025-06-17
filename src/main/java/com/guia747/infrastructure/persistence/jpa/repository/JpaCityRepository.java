package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCityEntity;
import com.guia747.infrastructure.persistence.jpa.entity.JpaStateEntity;

public interface JpaCityRepository extends JpaRepository<JpaCityEntity, UUID> {

    Page<JpaCityEntity> findByState(JpaStateEntity state, Pageable pageable);
}
