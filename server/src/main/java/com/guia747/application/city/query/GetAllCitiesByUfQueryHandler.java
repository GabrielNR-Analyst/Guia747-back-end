package com.guia747.application.city.query;

import org.springframework.data.domain.Page;
import com.guia747.web.dtos.city.CityDetailsResponse;

public interface GetAllCitiesByUfQueryHandler {

    Page<CityDetailsResponse> handle(GetAllCitiesByUfQuery query);
}
