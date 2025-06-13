package com.guia747.infrastructure.security;

public interface SecureRandomBase64TokenGenerator {

    String generate(int bytesLength);
}
