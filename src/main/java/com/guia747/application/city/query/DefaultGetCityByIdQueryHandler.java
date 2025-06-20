package com.guia747.application.city.query;

import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.city.entity.City;
import com.guia747.domain.city.exception.CityNotFoundException;
import com.guia747.domain.city.repository.CityRepository;
import com.guia747.web.dtos.city.CityDetailsResponse;

@Service
public class DefaultGetCityByIdQueryHandler implements GetCityByIdQueryHandler {

    private final CityRepository cityRepository;

    public DefaultGetCityByIdQueryHandler(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cities", key = "#cityId", unless = "#result == null")
    public CityDetailsResponse handle(UUID cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        return CityDetailsResponse.from(city);
    }
}
