package com.guia747.application.city.query;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.domain.city.repository.StateRepository;
import com.guia747.web.dtos.city.StateResponse;

@Service
public class DefaultGetAllStatesQueryHandler implements GetAllStatesQueryHandler {

    private final StateRepository stateRepository;

    public DefaultGetAllStatesQueryHandler(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "states", key = "'allStates'")
    public List<StateResponse> handle() {
        return stateRepository.findAll().stream().map(StateResponse::from).toList();
    }
}
