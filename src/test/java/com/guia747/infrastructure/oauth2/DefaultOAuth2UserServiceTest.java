package com.guia747.infrastructure.oauth2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guia747.infrastructure.config.OAuth2ProviderConfiguration;
import com.guia747.infrastructure.exception.OAuth2AuthenticationException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"rawtypes", "unchecked"})
class DefaultOAuth2UserServiceTest {

    @Mock
    private OAuth2ProviderConfiguration provider;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private DefaultOAuth2UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TEST_USERINFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo";
    private static final String TEST_SUB = "1234567890";
    private static final String TEST_NAME = "Test Name";
    private static final String TEST_PICTURE = "https://lh3.googleusercontent.com/photo.jpg";
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TEST_ACCESS_TOKEN = "test-access-token";

    @BeforeEach
    void setUp() {
        when(provider.getUserInfoUri()).thenReturn(TEST_USERINFO_URI);
    }

    @Test
    void shouldRetrieveUserProfile() {
        JsonNode mockResponse = objectMapper.createObjectNode()
                .put("sub", TEST_SUB)
                .put("name", TEST_NAME)
                .put("picture", TEST_PICTURE)
                .put("email", TEST_EMAIL);

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(eq("Authorization"), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(any(MediaType.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(JsonNode.class)).thenReturn(mockResponse);

        OAuth2UserProfile userProfile = userService.getUserProfile(TEST_ACCESS_TOKEN);

        assertThat(userProfile).isNotNull();
        assertThat(userProfile.getProviderId()).isEqualTo(TEST_SUB);
        assertThat(userProfile.getProviderName()).isEqualTo("Google");
        assertThat(userProfile.getName()).isEqualTo(TEST_NAME);
        assertThat(userProfile.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(userProfile.getPictureUrl()).isEqualTo(TEST_PICTURE);

        verify(restClient).get();
        verify(requestHeadersUriSpec).uri(TEST_USERINFO_URI);
        verify(requestHeadersSpec).header("Authorization", "Bearer " + TEST_ACCESS_TOKEN);
        verify(requestHeadersSpec).accept(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldThrowExceptionWhenResponseIsNull() {
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(eq("Authorization"), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(any(MediaType.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(JsonNode.class)).thenReturn(null);

        assertThatThrownBy(() -> userService.getUserProfile(TEST_ACCESS_TOKEN))
                .isInstanceOf(OAuth2AuthenticationException.class);
    }
}
