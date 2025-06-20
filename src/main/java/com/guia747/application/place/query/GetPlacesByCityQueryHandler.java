package com.guia747.application.place.query;

import org.springframework.data.domain.Page;
import com.guia747.domain.places.entity.Place;

public interface GetPlacesByCityQueryHandler {

    Page<Place> handle(GetPlacesByCityQuery query);
}
