package com.guia747.cities.repository;

import java.util.Optional;
import java.util.UUID;
import com.guia747.cities.entity.State;

public interface StateRepository {

    Optional<State> findById(UUID id);
}
