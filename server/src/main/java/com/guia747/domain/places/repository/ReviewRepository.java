package com.guia747.domain.places.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.guia747.domain.places.entity.Review;

public interface ReviewRepository {

    Review save(Review review);

    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);

    Page<Review> findAllByPlaceId(UUID placeId, Pageable pageable);
}
