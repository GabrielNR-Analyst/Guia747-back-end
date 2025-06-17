package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCityEntity;

public interface JpaCityRepository extends JpaRepository<JpaCityEntity, UUID> {

}
