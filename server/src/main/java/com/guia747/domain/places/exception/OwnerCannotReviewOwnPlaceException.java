package com.guia747.domain.places.exception;

import org.springframework.http.HttpStatus;
import com.guia747.shared.exception.AbstractApiException;

public class OwnerCannotReviewOwnPlaceException extends AbstractApiException {

    public OwnerCannotReviewOwnPlaceException() {
        super("Proprietários não podem avaliar seus próprios locais.");
    }

    @Override
    public String getErrorCode() {
        return "REVIEW_OWNER_NOT_ALLOWED";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
