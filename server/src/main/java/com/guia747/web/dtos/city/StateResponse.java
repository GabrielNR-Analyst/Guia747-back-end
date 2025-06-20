package com.guia747.web.dtos.city;

import com.guia747.domain.city.entity.State;

public record StateResponse(
        String name,
        String uf
) {

    public static StateResponse from(State state) {
        return new StateResponse(state.getName(), state.getUf());
    }
}
