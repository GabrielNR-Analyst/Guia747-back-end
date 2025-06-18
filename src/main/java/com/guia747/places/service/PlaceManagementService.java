package com.guia747.places.service;

import java.util.UUID;
import com.guia747.places.dto.CreatePlaceRequest;
import com.guia747.places.dto.PlaceDetailsResponse;
import com.guia747.places.entity.Place;

public interface PlaceManagementService {

    Place createPlace(UUID userId, CreatePlaceRequest request);

    PlaceDetailsResponse getPlaceDetail(UUID placeId);
}
