package com.guia747.application.city.query;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.city.repository.CityRepository;
import com.guia747.web.dtos.city.CityDetailsResponse;

@Service
public class DefaultGetAllCitiesQueryHandler implements GetAllCitiesQueryHandler {

    private final CityRepository cityRepository;

    public DefaultGetAllCitiesQueryHandler(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityDetailsResponse> handle(GetAllCitiesQuery query) {
        return cityRepository.findAll(query.search(), query.pageable()).map(CityDetailsResponse::from);
    }
}
