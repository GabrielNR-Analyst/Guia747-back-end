package com.guia747.cities.exception;

import com.guia747.common.ResourceNotFoundException;

public class CityNotFoundException extends ResourceNotFoundException {

    public CityNotFoundException() {
        super("Cidade não encontrada");
    }
}
