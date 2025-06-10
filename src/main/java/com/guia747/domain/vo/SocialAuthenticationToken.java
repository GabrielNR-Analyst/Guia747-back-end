package com.guia747.domain.vo;

public record SocialAuthenticationToken(String value) {

    public SocialAuthenticationToken {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Social authentication token cannot be null or blank");
        }
        if (value.length() > 4096) {
            throw new IllegalArgumentException("Social authentication token cannot be longer than 4096 characters");
        }
    }
}
