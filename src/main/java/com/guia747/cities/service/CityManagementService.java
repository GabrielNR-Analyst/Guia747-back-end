package com.guia747.cities.service;

import com.guia747.cities.dto.CreateCityRequest;
import com.guia747.cities.entity.City;

public interface CityManagementService {

    City createCity(CreateCityRequest request);
}
