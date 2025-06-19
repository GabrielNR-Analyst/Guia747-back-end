package com.guia747.common;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractApiException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "RESOURCE_NOT_FOUND";
    }
}
