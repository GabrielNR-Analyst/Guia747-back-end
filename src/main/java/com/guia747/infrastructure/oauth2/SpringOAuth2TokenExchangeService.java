package com.guia747.infrastructure.oauth2;

import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class SpringOAuth2TokenExchangeService implements OAuth2TokenExchangeService {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final RestClientAuthorizationCodeTokenResponseClient tokenResponseClient;

    public SpringOAuth2TokenExchangeService(ClientRegistrationRepository clientRegistrationRepository,
            RestClientAuthorizationCodeTokenResponseClient tokenResponseClient) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.tokenResponseClient = tokenResponseClient;
    }

    @Override
    public OAuth2TokenExchangeResponse exchangeAuthorizationCode(String providerName, String authorizationCode,
            String state, String codeVerifier) {

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(providerName);
        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest.authorizationCode()
                .clientId(clientRegistration.getClientId())
                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                .redirectUri(clientRegistration.getRedirectUri())
                .state(state)
                .attributes(attrs -> attrs.put("code_verifier", codeVerifier))
                .build();

        OAuth2AuthorizationResponse authorizationResponse = OAuth2AuthorizationResponse.success(authorizationCode)
                .redirectUri(clientRegistration.getRedirectUri())
                .state(state)
                .build();

        OAuth2AuthorizationExchange authorizationExchange = new OAuth2AuthorizationExchange(
                authorizationRequest,
                authorizationResponse
        );

        OAuth2AuthorizationCodeGrantRequest tokenRequest = new OAuth2AuthorizationCodeGrantRequest(
                clientRegistration,
                authorizationExchange
        );

        OAuth2AccessTokenResponse tokenResponse = tokenResponseClient.getTokenResponse(tokenRequest);

        return OAuth2TokenExchangeResponse.withToken(tokenResponse.getAccessToken().getTokenValue())
                .tokenType(tokenResponse.getAccessToken().getTokenType().getValue())
                .expiresAt(tokenResponse.getAccessToken().getExpiresAt() != null ?
                        tokenResponse.getAccessToken().getExpiresAt() : null)
                .refreshToken(tokenResponse.getRefreshToken() != null ?
                        tokenResponse.getRefreshToken().getTokenValue() : null)
                .build();
    }
}
