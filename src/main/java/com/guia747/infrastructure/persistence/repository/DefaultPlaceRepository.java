package com.guia747.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import com.guia747.infrastructure.persistence.jpa.entity.JpaPlaceEntity;
import com.guia747.infrastructure.persistence.jpa.repository.PlaceJpaRepository;
import com.guia747.infrastructure.persistence.mapper.PlaceJpaMapper;
import com.guia747.places.entity.Place;
import com.guia747.places.repository.PlaceRepository;

@Repository
public class DefaultPlaceRepository implements PlaceRepository {

    private final PlaceJpaRepository jpaRepository;
    private final PlaceJpaMapper mapper;

    public DefaultPlaceRepository(PlaceJpaRepository jpaRepository, PlaceJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Place save(Place place) {
        JpaPlaceEntity entity = mapper.toEntity(place);
        JpaPlaceEntity savedPlace = jpaRepository.save(entity);
        return mapper.toDomain(savedPlace);
    }

    @Override
    public Optional<Place> findById(UUID placeId) {
        return jpaRepository.findById(placeId).map(mapper::toDomain);
    }

    @Override
    public Page<Place> findByCityId(UUID cityId, PageRequest pageable) {
        return jpaRepository.findByCityId(cityId, pageable).map(mapper::toDomain);
    }
}
