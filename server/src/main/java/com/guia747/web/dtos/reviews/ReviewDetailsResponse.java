package com.guia747.web.dtos.reviews;

import java.time.LocalDateTime;
import java.util.UUID;
import com.guia747.domain.places.entity.Review;

public record ReviewDetailsResponse(UUID id, ReviewerResponse reviewer, Integer rating, String comment,
        LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ReviewDetailsResponse from(Review review) {
        ReviewerResponse reviewer = new ReviewerResponse(
                review.getReviewer().getId(),
                review.getReviewer().getName(),
                review.getReviewer().getProfilePictureUrl()
        );

        return new ReviewDetailsResponse(
                review.getId(),
                reviewer,
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    public record ReviewerResponse(UUID id, String name, String profilePictureUrl) {

    }
}
