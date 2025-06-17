package com.guia747.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;
import com.guia747.cities.entity.City;
import com.guia747.cities.repository.CityRepository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaCityEntity;
import com.guia747.infrastructure.persistence.jpa.repository.JpaCityRepository;
import com.guia747.infrastructure.persistence.mapper.CityMapper;

@Repository
public class DefaultCityRepository implements CityRepository {

    private final JpaCityRepository jpaRepository;
    private final CityMapper mapper;

    public DefaultCityRepository(JpaCityRepository jpaRepository, CityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public City save(City city) {
        JpaCityEntity entity = mapper.toEntity(city);
        JpaCityEntity savedCity = jpaRepository.save(entity);
        return mapper.toDomain(savedCity);
    }
}
