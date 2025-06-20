package com.guia747.infrastructure.oauth2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guia747.infrastructure.config.OAuth2ProviderConfiguration;
import com.guia747.infrastructure.exception.OAuth2AuthenticationException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoogleOAuth2TokenServiceTest {

    @Mock
    private OAuth2ProviderConfiguration provider;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private GoogleOAuth2TokenService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TEST_AUTHORIZATION_CODE = "authorizationCode";
    private static final String TEST_ACCESS_TOKEN = "ya29.test-access-token";

    @Captor
    private ArgumentCaptor<MultiValueMap<String, String>> formDataCaptor;

    private static final String TEST_CLIENT_ID = "test-client-id";
    private static final String TEST_CLIENT_SECRET = "test-client-secret";
    private static final String TEST_TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String TEST_REDIRECT_URI = "http://localhost:3000";

    @BeforeEach
    void setUp() {
        when(provider.getClientId()).thenReturn(TEST_CLIENT_ID);
        when(provider.getClientSecret()).thenReturn(TEST_CLIENT_SECRET);
        when(provider.getTokenUri()).thenReturn(TEST_TOKEN_URI);
        when(provider.getRedirectUri()).thenReturn(TEST_REDIRECT_URI);
    }

    @Test
    void shouldExchangeCodeForAccessTokenSuccessfully() {
        JsonNode mockResponse = objectMapper.createObjectNode()
                .put("access_token", TEST_ACCESS_TOKEN)
                .put("token_type", "Bearer")
                .put("expires_in", 3600);

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(JsonNode.class)).thenReturn(mockResponse);

        String actualAccessToken = service.exchangeCodeForAccessToken(TEST_AUTHORIZATION_CODE);

        assertThat(actualAccessToken).isEqualTo(TEST_ACCESS_TOKEN);

        verify(restClient).post();
        verify(requestBodyUriSpec).uri(TEST_TOKEN_URI);
        verify(requestBodySpec).contentType(MediaType.APPLICATION_FORM_URLENCODED);
        verify(requestBodySpec).body(formDataCaptor.capture());

        // Verify form data
        MultiValueMap<String, String> formData = formDataCaptor.getValue();
        assertThat(formData.getFirst("grant_type")).isEqualTo("authorization_code");
        assertThat(formData.getFirst("client_id")).isEqualTo(TEST_CLIENT_ID);
        assertThat(formData.getFirst("client_secret")).isEqualTo(TEST_CLIENT_SECRET);
        assertThat(formData.getFirst("code")).isEqualTo(TEST_AUTHORIZATION_CODE);
        assertThat(formData.getFirst("redirect_uri")).isEqualTo(TEST_REDIRECT_URI);
    }

    @Test
    void shouldThrowExceptionWhenResponseIsNull() {
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(JsonNode.class)).thenReturn(null);

        assertThatThrownBy(() -> service.exchangeCodeForAccessToken(TEST_AUTHORIZATION_CODE))
                .isInstanceOf(OAuth2AuthenticationException.class);
    }

    @Test
    void shouldThrowExceptionWhenTokenIsMissing() {
        JsonNode mockResponse = objectMapper.createObjectNode()
                .put("token_type", "Bearer")
                .put("expires_in", 3600);

        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(JsonNode.class)).thenReturn(mockResponse);

        assertThatThrownBy(() -> service.exchangeCodeForAccessToken(TEST_AUTHORIZATION_CODE))
                .isInstanceOf(OAuth2AuthenticationException.class);
    }

    @Test
    void shouldThrowExceptionWhenRestClientFails() {
        when(restClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(MultiValueMap.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenThrow(new RestClientException("Network error"));

        assertThatThrownBy(() -> service.exchangeCodeForAccessToken(TEST_AUTHORIZATION_CODE))
                .isInstanceOf(RestClientException.class);
    }
}
