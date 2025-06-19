package com.guia747.cities.exception;

import com.guia747.common.ResourceNotFoundException;

public class StateNotFoundException extends ResourceNotFoundException {

    public StateNotFoundException() {
        super("Estado não encontrado");
    }
}
