package com.guia747.domain.places.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.guia747.domain.places.entity.Review;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findById(UUID id);

    void deleteById(UUID id);

    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);

    Page<Review> findAllByPlaceId(UUID placeId, Pageable pageable);
}
