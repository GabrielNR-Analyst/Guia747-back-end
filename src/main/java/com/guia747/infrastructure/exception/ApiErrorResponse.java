package com.guia747.infrastructure.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private int status;
    private String message;
    private List<ApiValidationError> errors;

    private ApiErrorResponse() {
    }

    public static ApiErrorResponse createNew(int status, String message, List<ApiValidationError> errors) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.status = status;
        response.message = message;
        response.errors = errors;
        return response;
    }

    public static ApiErrorResponse createNew(int status, String message) {
        return createNew(status, message, null);
    }

    public static ApiErrorResponse createNew(HttpStatusCode status, String message) {
        return createNew(status.value(), message);
    }

    public static ApiErrorResponse validationError(List<ApiValidationError> errors) {
        return createNew(HttpStatus.BAD_REQUEST.value(),
                "A requisição contém dados inválidos. Por favor, revise todos os campos.");
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ApiValidationError> getErrors() {
        return errors;
    }
}
