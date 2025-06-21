package com.guia747.application.place.usecase;

import java.util.UUID;
import com.guia747.web.dtos.reviews.CreateReviewRequest;
import com.guia747.web.dtos.reviews.ReviewDetailsResponse;

public interface CreateReviewUseCase {

    ReviewDetailsResponse execute(UUID placeId, UUID userId, CreateReviewRequest request);
}
