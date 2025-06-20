package com.guia747.infrastructure.security;

public interface HashService {

    byte[] hash(byte[] input);

    String hashToBase64(byte[] input);
}
