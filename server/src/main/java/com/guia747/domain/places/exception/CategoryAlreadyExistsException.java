package com.guia747.domain.places.exception;

import org.springframework.http.HttpStatus;
import com.guia747.shared.exception.AbstractApiException;

public class CategoryAlreadyExistsException extends AbstractApiException {

    public CategoryAlreadyExistsException(String name) {
        super(String.format("A categoria com o nome '%s' já existe. Por favor, escolha outro nome.", name));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getErrorCode() {
        return "CategoryAlreadyExists";
    }
}
