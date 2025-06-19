package com.guia747.domain.city.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.guia747.domain.city.entity.State;

public interface StateRepository {

    Optional<State> findById(UUID id);

    Optional<State> findByUf(String uf);

    List<State> findAll();
}
