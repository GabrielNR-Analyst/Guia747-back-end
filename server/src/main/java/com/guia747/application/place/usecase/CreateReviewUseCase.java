package com.guia747.application.place.usecase;

import java.util.UUID;
import com.guia747.web.dtos.reviews.CreateReviewRequest;
import com.guia747.web.dtos.reviews.CreateReviewResponse;

public interface CreateReviewUseCase {

    CreateReviewResponse execute(UUID placeId, UUID userId, CreateReviewRequest request);
}
