package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaReviewEntity;

public interface JpaReviewRepository extends JpaRepository<JpaReviewEntity, UUID> {

    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);
}
