package com.guia747.infrastructure.exception;

public record ApiValidationError(String message, String fieldName) {
}
