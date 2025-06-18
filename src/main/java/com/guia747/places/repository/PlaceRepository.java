package com.guia747.places.repository;

import java.util.Optional;
import java.util.UUID;
import com.guia747.places.entity.Place;

public interface PlaceRepository {

    Place save(Place place);

    Optional<Place> findById(UUID placeId);
}
