package com.guia747.places.service;

import java.util.UUID;
import jakarta.validation.Valid;
import com.guia747.places.dto.CreatePlaceRequest;
import com.guia747.places.dto.PlaceDetailsResponse;
import com.guia747.places.dto.UpdatePlaceRequest;
import com.guia747.places.entity.Place;

public interface PlaceManagementService {

    Place createPlace(UUID userId, CreatePlaceRequest request);

    PlaceDetailsResponse getPlaceDetail(UUID placeId);

    void updatePlace(UUID placeId, @Valid UpdatePlaceRequest request);
}
