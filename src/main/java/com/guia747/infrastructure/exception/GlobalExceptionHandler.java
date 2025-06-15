package com.guia747.infrastructure.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        ApiErrorResponse builder = ApiErrorResponse.status(status)
                .error("method_argument_not_valid")
                .message("A requisição contém dados inválidos. Por favor, revise todos os campos.")
                .details(details -> ex.getBindingResult().getFieldErrors().forEach(error ->
                        details.add(error.getField(), error.getDefaultMessage())
                ))
                .build();

        return ResponseEntity.status(status).body(builder);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        ApiErrorResponse response = ApiErrorResponse.status(status)
                .error("message_not_readable")
                .message("O corpo da requisição está malformado ou ilegível.")
                .build();

        return ResponseEntity.status(status).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        ApiErrorResponse response = ApiErrorResponse.status(status)
                .error("http_method_not_supported")
                .message("O método HTTP utilizado não é suportado para este endpoint.")
                .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<Object> handleMissing(MissingRequestCookieException ex) {
        ApiErrorResponse response = ApiErrorResponse.status(ex.getStatusCode())
                .error("missing_required_cookie")
                .message(String.format("O cookie obrigatório '%s' não foi fornecido na requisição.",
                        ex.getCookieName()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
        ApiErrorResponse response = ApiErrorResponse.status(HttpStatus.UNAUTHORIZED)
                .error("unauthorized")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
