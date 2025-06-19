package com.guia747.domain.city.exception;

import com.guia747.shared.exception.ResourceNotFoundException;

public class StateNotFoundException extends ResourceNotFoundException {

    public StateNotFoundException() {
        super("Estado não encontrado");
    }
}
