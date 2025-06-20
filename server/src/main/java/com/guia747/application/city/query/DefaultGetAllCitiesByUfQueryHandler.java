package com.guia747.application.city.query;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.city.entity.State;
import com.guia747.domain.city.exception.StateNotFoundException;
import com.guia747.domain.city.repository.CityRepository;
import com.guia747.domain.city.repository.StateRepository;
import com.guia747.web.dtos.city.CityDetailsResponse;

@Service
public class DefaultGetAllCitiesByUfQueryHandler implements GetAllCitiesByUfQueryHandler {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public DefaultGetAllCitiesByUfQueryHandler(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityDetailsResponse> handle(GetAllCitiesByUfQuery query) {
        State state = stateRepository.findByUf(query.uf()).orElseThrow(StateNotFoundException::new);
        return cityRepository.findAllByState(state, query.pageable())
                .map(CityDetailsResponse::from);
    }
}
