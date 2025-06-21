package com.guia747.domain.places.repository;

import java.util.UUID;
import com.guia747.domain.places.entity.Review;

public interface ReviewRepository {

    Review save(Review review);

    boolean existsByPlaceIdAndReviewerId(UUID placeId, UUID reviewerId);
}
