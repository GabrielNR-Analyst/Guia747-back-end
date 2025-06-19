package com.guia747.domain.places.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.guia747.domain.places.entity.Place;

public interface PlaceRepository {

    Place save(Place place);

    Optional<Place> findById(UUID placeId);

    Page<Place> findByCityId(UUID cityId, PageRequest pageable);
}
