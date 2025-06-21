package com.guia747.web.dtos.reviews;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReviewResponse(UUID id, Integer rating, String comment, LocalDateTime createdAt) {

}
