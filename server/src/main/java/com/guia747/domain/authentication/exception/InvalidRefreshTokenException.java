package com.guia747.domain.authentication.exception;

import org.springframework.http.HttpStatus;
import com.guia747.shared.exception.AbstractApiException;

public class InvalidRefreshTokenException extends AbstractApiException {

    public InvalidRefreshTokenException() {
        super("The provided refresh token is invalid or expired");
    }

    @Override
    public String getErrorCode() {
        return "INVALID_TOKEN";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
