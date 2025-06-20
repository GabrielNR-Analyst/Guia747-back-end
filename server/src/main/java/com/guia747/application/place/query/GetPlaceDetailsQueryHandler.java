package com.guia747.application.place.query;

import com.guia747.web.dtos.place.PlaceDetailsResponse;

public interface GetPlaceDetailsQueryHandler {

    PlaceDetailsResponse handle(GetPlaceDetailsQuery query);
}
