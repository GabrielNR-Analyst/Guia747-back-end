package com.guia747.domain.places.exception;

import com.guia747.shared.exception.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {

    public CategoryNotFoundException() {
        super("Categoria não encontrada");
    }
}
