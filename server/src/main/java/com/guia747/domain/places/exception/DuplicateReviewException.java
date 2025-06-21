package com.guia747.domain.places.exception;

import org.springframework.http.HttpStatus;
import com.guia747.shared.exception.AbstractApiException;

public class DuplicateReviewException extends AbstractApiException {

    public DuplicateReviewException() {
        super("Usuário já avaliou este local.");
    }

    @Override
    public String getErrorCode() {
        return "REVIEW_DUPLICATE";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
