package com.guia747.application.place.query;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.places.exception.PlaceNotFoundException;
import com.guia747.domain.places.repository.PlaceRepository;
import com.guia747.domain.places.repository.ReviewRepository;
import com.guia747.web.dtos.reviews.ReviewDetailsResponse;

@Service
public class DefaultGetAllReviewsByPlaceIdHandler implements GetAllReviewsByPlaceIdHandler {

    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public DefaultGetAllReviewsByPlaceIdHandler(PlaceRepository placeRepository, ReviewRepository reviewRepository) {
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDetailsResponse> handle(UUID placeId, Pageable pageable) {
        placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);
        return reviewRepository.findAllByPlaceId(placeId, pageable).map(ReviewDetailsResponse::from);
    }
}
