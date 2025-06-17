package com.guia747.cities.service;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.guia747.cities.entity.State;
import com.guia747.cities.repository.StateRepository;

@Service
public class DefaultStateManagementService implements StateManagementService {

    private final StateRepository stateRepository;

    public DefaultStateManagementService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    @Transactional
    @Cacheable(value = "states", key = "'allStates'")
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }
}
