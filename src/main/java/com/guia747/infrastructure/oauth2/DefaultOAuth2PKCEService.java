package com.guia747.infrastructure.oauth2;

import org.springframework.stereotype.Service;
import com.guia747.infrastructure.security.HashService;
import com.guia747.infrastructure.security.SecureRandomBase64TokenGenerator;

@Service
public class DefaultOAuth2PKCEService implements OAuth2PKCEService {

    private static final int CODE_VERIFIER_LENGTH = 96;

    private final SecureRandomBase64TokenGenerator secureRandomBase64TokenGenerator;
    private final HashService hashService;

    public DefaultOAuth2PKCEService(SecureRandomBase64TokenGenerator secureRandomBase64TokenGenerator,
            HashService hashService) {
        this.secureRandomBase64TokenGenerator = secureRandomBase64TokenGenerator;
        this.hashService = hashService;
    }

    @Override
    public String generateCodeVerifier() {
        return secureRandomBase64TokenGenerator.generate(CODE_VERIFIER_LENGTH);
    }

    @Override
    public String generateS256CodeChallenge(String codeVerifier) {
        return hashService.hashToBase64(codeVerifier.getBytes());
    }
}
