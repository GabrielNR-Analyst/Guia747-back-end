package com.guia747.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.guia747.cities.entity.City;
import com.guia747.cities.entity.State;
import com.guia747.cities.repository.CityRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCityEntity;
import com.guia747.infrastructure.persistence.jpa.entity.JpaStateEntity;
import com.guia747.infrastructure.persistence.jpa.repository.JpaCityRepository;
import com.guia747.infrastructure.persistence.mapper.CityMapper;
import com.guia747.infrastructure.persistence.mapper.StateMapper;

@Repository
public class DefaultCityRepository implements CityRepository {

    private final JpaCityRepository jpaRepository;
    private final CityMapper cityMapper;
    private final StateMapper stateMapper;

    public DefaultCityRepository(JpaCityRepository jpaRepository, CityMapper cityMapper, StateMapper stateMapper) {
        this.jpaRepository = jpaRepository;
        this.cityMapper = cityMapper;
        this.stateMapper = stateMapper;
    }

    @Override
    public City save(City city) {
        JpaCityEntity entity = cityMapper.toEntity(city);
        JpaCityEntity savedCity = jpaRepository.save(entity);
        return cityMapper.toDomain(savedCity);
    }

    @Override
    public Page<City> findAllByState(State state, Pageable pageable) {
        JpaStateEntity entity = stateMapper.toEntity(state);
        return jpaRepository.findByState(entity, pageable).map(cityMapper::toDomain);
    }
}
