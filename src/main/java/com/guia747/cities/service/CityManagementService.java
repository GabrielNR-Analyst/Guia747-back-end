package com.guia747.cities.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.guia747.cities.dto.CreateCityRequest;
import com.guia747.cities.entity.City;

public interface CityManagementService {

    City createCity(CreateCityRequest request);

    Page<City> getAllCitiesByUf(String uf, Pageable pageable);
}
