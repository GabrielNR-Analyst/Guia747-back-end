package com.guia747.infrastructure.exception;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.guia747.domain.exception.InvalidSocialAuthenticationTokenException;
import com.guia747.domain.exception.SocialAuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        List<ApiValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ApiValidationError(err.getField(), err.getDefaultMessage()))
                .toList();

        return ResponseEntity.status(status).body(ApiErrorResponse.validationError(errors));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        var response = ApiErrorResponse.createNew(status, "O corpo da requisição está malformado ou ilegível.");
        return ResponseEntity.status(status).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        var response = ApiErrorResponse.createNew(status,
                "O método HTTP utilizado não é suportado para este endpoint.");
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        String headerName = ex.getHeaderName();
        var message = String.format("O cabeçalho HTTP obrigatório '%s' não foi fornecido na requisição.", headerName);
        var response = ApiErrorResponse.createNew(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        LOGGER.error("Unhandled exception: ", ex);
        var response = ApiErrorResponse.createNew(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(SocialAuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleSocialAuthenticationException(SocialAuthenticationException ex) {
        if (ex instanceof InvalidSocialAuthenticationTokenException) {
            var response = ApiErrorResponse.createNew(HttpStatus.UNAUTHORIZED, ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        var response = ApiErrorResponse.createNew(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
