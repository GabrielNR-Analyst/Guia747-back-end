package com.guia747.application.place.usecase;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.places.entity.Place;
import com.guia747.domain.places.entity.Review;
import com.guia747.domain.places.exception.DuplicateReviewException;
import com.guia747.domain.places.exception.OwnerCannotReviewOwnPlaceException;
import com.guia747.domain.places.exception.PlaceNotFoundException;
import com.guia747.domain.places.repository.PlaceRepository;
import com.guia747.domain.places.repository.ReviewRepository;
import com.guia747.domain.users.entity.User;
import com.guia747.domain.users.exception.UserNotFoundException;
import com.guia747.domain.users.repository.UserRepository;
import com.guia747.web.dtos.reviews.CreateReviewRequest;
import com.guia747.web.dtos.reviews.CreateReviewResponse;

@Service
public class DefaultCreateReviewUseCase implements CreateReviewUseCase {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public DefaultCreateReviewUseCase(
            ReviewRepository reviewRepository,
            PlaceRepository placeRepository,
            UserRepository userRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateReviewResponse execute(UUID placeId, UUID userId, CreateReviewRequest request) {
        if (isPlaceOwner(placeId, userId)) {
            throw new OwnerCannotReviewOwnPlaceException();
        }

        if (reviewRepository.existsByPlaceIdAndReviewerId(placeId, userId)) {
            throw new DuplicateReviewException();
        }

        Place place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Review review = Review.createNew(place, user, request.rating(), request.comment());
        Review savedReview = reviewRepository.save(review);

        return new CreateReviewResponse(
                savedReview.getId(),
                savedReview.getRating(),
                savedReview.getComment(),
                savedReview.getCreatedAt()
        );
    }

    private boolean isPlaceOwner(UUID placeId, UUID userId) {
        return placeRepository.findById(placeId)
                .map(place -> place.isOwnedBy(userId))
                .orElse(false);
    }
}
