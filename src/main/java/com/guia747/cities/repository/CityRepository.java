package com.guia747.cities.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.guia747.cities.entity.City;
import com.guia747.cities.entity.State;

public interface CityRepository {

    City save(City city);

    Page<City> findAllByState(State state, Pageable pageable);
}
