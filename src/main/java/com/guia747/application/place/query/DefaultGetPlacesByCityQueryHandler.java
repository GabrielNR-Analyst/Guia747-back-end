package com.guia747.application.place.query;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.places.entity.Place;
import com.guia747.domain.places.repository.PlaceRepository;

@Service
public class DefaultGetPlacesByCityQueryHandler implements GetPlacesByCityQueryHandler {

    private final PlaceRepository placeRepository;

    public DefaultGetPlacesByCityQueryHandler(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Place> handle(GetPlacesByCityQuery query) {
        return placeRepository.findByCityId(query.cityId(), query.pageable());
    }
}
