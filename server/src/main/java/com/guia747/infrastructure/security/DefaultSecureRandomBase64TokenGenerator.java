package com.guia747.infrastructure.security;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class DefaultSecureRandomBase64TokenGenerator implements SecureRandomBase64TokenGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generate(int bytesLength) {
        byte[] bytes = new byte[bytesLength];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
