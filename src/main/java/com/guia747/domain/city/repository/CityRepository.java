package com.guia747.domain.city.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.guia747.domain.city.entity.City;
import com.guia747.domain.city.entity.State;

public interface CityRepository {

    City save(City city);

    Optional<City> findById(UUID id);

    Page<City> findAllByState(State state, Pageable pageable);

    Page<City> findAll(String search, Pageable pageable);

    boolean existsBySlug(String slug);
}
