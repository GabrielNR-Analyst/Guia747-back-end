package com.guia747.cities.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.guia747.cities.dto.CreateCityRequest;
import com.guia747.cities.dto.UpdateCityRequest;
import com.guia747.cities.entity.City;

public interface CityManagementService {

    City createCity(CreateCityRequest request);

    Page<City> getAllCitiesByUf(String uf, Pageable pageable);

    Page<City> getAllCities(String search, Pageable pageable);

    City updateCity(UUID cityId, UpdateCityRequest request);
}
