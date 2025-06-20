package com.guia747.application.city.usecase;

import java.util.UUID;
import com.guia747.web.dtos.city.UpdateCityRequest;
import com.guia747.web.dtos.city.CityDetailsResponse;

public interface UpdateCityUseCase {

    CityDetailsResponse execute(UUID cityId, UpdateCityRequest request);
}
