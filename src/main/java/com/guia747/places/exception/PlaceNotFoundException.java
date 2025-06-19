package com.guia747.places.exception;

import com.guia747.common.ResourceNotFoundException;

public class PlaceNotFoundException extends ResourceNotFoundException {

    public PlaceNotFoundException() {
        super("Local não encontrado");
    }
}
