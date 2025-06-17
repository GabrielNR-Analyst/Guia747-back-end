package com.guia747.cities.dto;

import java.util.UUID;
import com.guia747.cities.entity.State;

public record StateResponse(
        UUID id,
        String name,
        String uf
) {

    public static StateResponse from(State state) {
        return new StateResponse(state.getId(), state.getName(), state.getUf());
    }
}
