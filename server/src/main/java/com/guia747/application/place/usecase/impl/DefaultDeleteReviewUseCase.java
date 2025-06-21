package com.guia747.application.place.usecase.impl;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.application.place.usecase.DeleteReviewUseCase;
import com.guia747.domain.places.entity.Review;
import com.guia747.domain.places.exception.ReviewNotFoundException;
import com.guia747.domain.places.exception.ReviewOwnershipException;
import com.guia747.domain.places.repository.ReviewRepository;

@Service
public class DefaultDeleteReviewUseCase implements DeleteReviewUseCase {

    private final ReviewRepository reviewRepository;

    public DefaultDeleteReviewUseCase(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void execute(UUID reviewId, UUID userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        if (!review.isOwnedBy(userId)) {
            throw new ReviewOwnershipException();
        }

        reviewRepository.deleteById(reviewId);
    }
}
