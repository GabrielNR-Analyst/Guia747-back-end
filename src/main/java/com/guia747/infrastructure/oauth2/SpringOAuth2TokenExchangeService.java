package com.guia747.infrastructure.oauth2;

import java.time.Instant;
import java.util.Map;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.guia747.authentication.OAuth2UserInfo;

@Service
public class SpringOAuth2TokenExchangeService implements OAuth2TokenExchangeService {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final RestClientAuthorizationCodeTokenResponseClient tokenResponseClient;
    private final DefaultOAuth2UserService oauth2UserService;

    public SpringOAuth2TokenExchangeService(ClientRegistrationRepository clientRegistrationRepository,
            RestClientAuthorizationCodeTokenResponseClient tokenResponseClient,
            DefaultOAuth2UserService oauth2UserService) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.tokenResponseClient = tokenResponseClient;
        this.oauth2UserService = oauth2UserService;
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

    @Override
    public OAuth2UserInfo fetchUserInfo(String providerName, String accessToken) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(providerName);
        OAuth2AccessToken oauth2AccessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(), Instant.now().plusSeconds(3600)
        );

        OAuth2UserRequest userRequest = new OAuth2UserRequest(clientRegistration, oauth2AccessToken);
        OAuth2User oauth2User = oauth2UserService.loadUser(userRequest);

        Map<String, Object> attributes = oauth2User.getAttributes();
        return OAuth2UserInfo.withProvider((String) attributes.get("sub"))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profilePictureUrl((String) attributes.get("picture"))
                .build();
    }
}
