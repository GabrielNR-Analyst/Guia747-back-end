package com.guia747.domain.places.exception;

import com.guia747.shared.exception.ResourceNotFoundException;

public class ReviewNotFoundException extends ResourceNotFoundException {

    public ReviewNotFoundException() {
        super("A avaliação especificada não existe ou foi removida");
    }
}
