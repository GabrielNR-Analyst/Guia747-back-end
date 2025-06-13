package com.guia747.infrastructure.oauth2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import com.guia747.infrastructure.security.SecureRandomBase64TokenGenerator;

@Service
public class SpringOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2PKCEService oauth2PkceService;
    private final SecureRandomBase64TokenGenerator secureRandomBase64TokenGenerator;

    public SpringOAuth2AuthorizationService(ClientRegistrationRepository clientRegistrationRepository,
            OAuth2PKCEService oauth2PkceService, SecureRandomBase64TokenGenerator secureRandomBase64TokenGenerator) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.oauth2PkceService = oauth2PkceService;
        this.secureRandomBase64TokenGenerator = secureRandomBase64TokenGenerator;
    }

    @Override
    public OAuth2AuthorizationResult generateAuthorizationUrl(String providerName) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(providerName);
        if (clientRegistration == null) {
            throw new UnsupportedSocialProviderException(
                    String.format("Provider '%s' is not supported.", providerName));
        }

        String codeVerifier = oauth2PkceService.generateCodeVerifier();
        String codeChallenge = oauth2PkceService.generateS256CodeChallenge(codeVerifier);

        String state = secureRandomBase64TokenGenerator.generate(16);

        OAuth2SecurityContext securityContext = new OAuth2SecurityContext(codeVerifier, state, providerName);
        String authorizationUrl = buildAuthorizationUrl(clientRegistration, state, codeChallenge);

        return new OAuth2AuthorizationResult(authorizationUrl, securityContext);
    }

    private String buildAuthorizationUrl(ClientRegistration clientRegistration, String state, String codeChallenge) {
        String scopesStr = String.join(" ", clientRegistration.getScopes());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(
                        clientRegistration.getProviderDetails().getAuthorizationUri())
                .queryParam("client_id", clientRegistration.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", clientRegistration.getRedirectUri())
                .queryParam("scope", URLEncoder.encode(scopesStr, StandardCharsets.UTF_8))
                .queryParam("state", URLEncoder.encode(state, StandardCharsets.UTF_8))
                .queryParam("code_challenge", URLEncoder.encode(codeChallenge, StandardCharsets.UTF_8))
                .queryParam("code_challenge_method", "S256");

        return uriBuilder.build().toUriString();
    }
}
