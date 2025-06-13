package com.guia747.infrastructure.oauth2;

public interface OAuth2PKCEService {

    String generateCodeVerifier();

    String generateS256CodeChallenge(String codeVerifier);
}
