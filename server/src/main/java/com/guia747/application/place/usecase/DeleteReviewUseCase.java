package com.guia747.application.place.usecase;

import java.util.UUID;

public interface DeleteReviewUseCase {

    void execute(UUID reviewId, UUID userId);
}
