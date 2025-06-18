package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaPlaceEntity;

public interface PlaceJpaRepository extends JpaRepository<JpaPlaceEntity, UUID> {

}
