package com.guia747.cities.dto;

import com.guia747.cities.entity.State;

public record StateResponse(
        String name,
        String uf
) {

    public static StateResponse from(State state) {
        return new StateResponse(state.getName(), state.getUf());
    }
}
