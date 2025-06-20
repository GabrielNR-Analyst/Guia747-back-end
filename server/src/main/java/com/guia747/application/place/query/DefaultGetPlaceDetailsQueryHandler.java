package com.guia747.application.place.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.places.entity.Place;
import com.guia747.domain.places.exception.PlaceNotFoundException;
import com.guia747.domain.places.repository.PlaceRepository;
import com.guia747.web.dtos.place.PlaceDetailsResponse;

@Service
public class DefaultGetPlaceDetailsQueryHandler implements GetPlaceDetailsQueryHandler {

    private final PlaceRepository placeRepository;

    public DefaultGetPlaceDetailsQueryHandler(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceDetailsResponse handle(GetPlaceDetailsQuery query) {
        Place place = placeRepository.findById(query.placeId())
                .orElseThrow(PlaceNotFoundException::new);
        return PlaceDetailsResponse.from(place);
    }
}
