package com.guia747.infrastructure.oauth2;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.guia747.infrastructure.config.OAuth2ProviderConfiguration;
import com.guia747.infrastructure.exception.OAuth2AuthenticationException;

@Service
public class GoogleOAuth2TokenService implements OAuth2TokenService {

    private final OAuth2ProviderConfiguration provider;
    private final RestClient restClient;

    public GoogleOAuth2TokenService(OAuth2ProviderConfiguration provider, RestClient restClient) {
        this.provider = provider;
        this.restClient = restClient;
    }

    @Override
    public String exchangeCodeForAccessToken(String authorizationCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("code", authorizationCode);
        formData.add("redirect_uri", provider.getRedirectUri());

        JsonNode response = restClient.post()
                .uri(provider.getTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(JsonNode.class);

        if (response == null || !response.has("access_token")) {
            throw new OAuth2AuthenticationException("Invalid token response from google provider");
        }

        return response.get("access_token").asText();
    }
}
