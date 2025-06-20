package com.guia747.application.city.usecase;

import com.guia747.web.dtos.city.CreateCityRequest;
import com.guia747.web.dtos.city.CreateCityResponse;

public interface CreateCityUseCase {

    CreateCityResponse execute(CreateCityRequest request);
}
