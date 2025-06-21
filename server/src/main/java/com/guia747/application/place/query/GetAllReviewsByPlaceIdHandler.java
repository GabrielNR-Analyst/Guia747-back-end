package com.guia747.application.place.query;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.guia747.web.dtos.reviews.ReviewDetailsResponse;

public interface GetAllReviewsByPlaceIdHandler {

    Page<ReviewDetailsResponse> handle(UUID placeId, Pageable pageable);
}
