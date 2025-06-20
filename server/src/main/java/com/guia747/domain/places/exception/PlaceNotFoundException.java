package com.guia747.domain.places.exception;

import com.guia747.shared.exception.ResourceNotFoundException;

public class PlaceNotFoundException extends ResourceNotFoundException {

    public PlaceNotFoundException() {
        super("Local não encontrado");
    }
}
