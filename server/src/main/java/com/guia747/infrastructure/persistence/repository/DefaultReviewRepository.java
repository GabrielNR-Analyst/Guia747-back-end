package com.guia747.infrastructure.persistence.repository;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.guia747.domain.places.entity.Review;
import com.guia747.domain.places.repository.ReviewRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaReviewEntity;
import com.guia747.infrastructure.persistence.jpa.repository.JpaReviewRepository;
import com.guia747.infrastructure.persistence.mapper.JpaReviewMapper;

@Repository
public class DefaultReviewRepository implements ReviewRepository {

    private final JpaReviewMapper mapper;
    private final JpaReviewRepository jpaRepository;

    public DefaultReviewRepository(JpaReviewMapper mapper, JpaReviewRepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Review save(Review review) {
        JpaReviewEntity entity = mapper.toEntity(review);
        JpaReviewEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId) {
        return jpaRepository.existsByPlaceIdAndReviewerId(placeId, reviewerId);
    }
}
