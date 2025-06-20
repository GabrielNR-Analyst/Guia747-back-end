package com.guia747.infrastructure.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Sha256HashService implements HashService {

    private static final String ALGORITHM = "SHA-256";

    @Override
    public byte[] hash(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    @Override
    public String hashToBase64(byte[] input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash(input));
    }
}
