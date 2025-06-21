package com.guia747.domain.places.exception;

import org.springframework.http.HttpStatus;
import com.guia747.shared.exception.AbstractApiException;

public class ReviewOwnershipException extends AbstractApiException {

    public ReviewOwnershipException() {
        super("Users can only delete their own reviews");
    }

    @Override
    public String getErrorCode() {
        return "REVIEW_FORBIDDEN_DELETE";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }
}
