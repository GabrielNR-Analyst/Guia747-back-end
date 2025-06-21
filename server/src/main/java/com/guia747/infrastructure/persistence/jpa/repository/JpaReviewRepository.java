package com.guia747.infrastructure.persistence.jpa.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaReviewEntity;

public interface JpaReviewRepository extends JpaRepository<JpaReviewEntity, UUID> {

    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);

    Page<JpaReviewEntity> findAllByPlaceId(UUID placeId, Pageable pageable);
}
