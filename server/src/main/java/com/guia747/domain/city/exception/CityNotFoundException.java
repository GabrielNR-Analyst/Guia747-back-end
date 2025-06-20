package com.guia747.domain.city.exception;

import com.guia747.shared.exception.ResourceNotFoundException;

public class CityNotFoundException extends ResourceNotFoundException {

    public CityNotFoundException() {
        super("Cidade não encontrada");
    }
}
