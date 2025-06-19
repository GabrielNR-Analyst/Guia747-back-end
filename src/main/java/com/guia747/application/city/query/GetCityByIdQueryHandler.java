package com.guia747.application.city.query;

import java.util.UUID;
import com.guia747.web.dtos.city.CityDetailsResponse;

public interface GetCityByIdQueryHandler {

    CityDetailsResponse handle(UUID cityId);
}
