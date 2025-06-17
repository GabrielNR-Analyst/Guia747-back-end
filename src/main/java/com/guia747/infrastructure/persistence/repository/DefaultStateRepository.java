package com.guia747.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.guia747.cities.entity.State;
import com.guia747.cities.repository.StateRepository;
import com.guia747.infrastructure.persistence.jpa.repository.JpaStateRepository;
import com.guia747.infrastructure.persistence.mapper.StateMapper;

@Repository
public class DefaultStateRepository implements StateRepository {

    private final JpaStateRepository jpaRepository;
    private final StateMapper mapper;

    public DefaultStateRepository(JpaStateRepository jpaRepository, StateMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<State> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<State> findByUf(String uf) {
        return jpaRepository.findByUf(uf).map(mapper::toDomain);
    }

    @Override
    public List<State> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
