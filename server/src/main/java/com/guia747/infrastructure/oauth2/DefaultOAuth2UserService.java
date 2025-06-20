package com.guia747.infrastructure.oauth2;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.guia747.infrastructure.config.OAuth2ProviderConfiguration;
import com.guia747.infrastructure.exception.OAuth2AuthenticationException;

@Service
public class DefaultOAuth2UserService implements OAuth2UserService {

    private final OAuth2ProviderConfiguration provider;
    private final RestClient restClient;

    public DefaultOAuth2UserService(OAuth2ProviderConfiguration provider, RestClient restClient) {
        this.provider = provider;
        this.restClient = restClient;
    }

    @Override
    public OAuth2UserProfile getUserProfile(String accessToken) {
        JsonNode response = restClient.get()
                .uri(provider.getUserInfoUri())
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(JsonNode.class);

        if (response == null) {
            throw new OAuth2AuthenticationException("User info response was null");
        }

        return OAuth2UserProfile.withGoogleProvider(response.get("sub").asText())
                .name(response.get("name").asText())
                .email(response.get("email").asText())
                .pictureUrl(response.get("picture").asText())
                .build();
    }
}
