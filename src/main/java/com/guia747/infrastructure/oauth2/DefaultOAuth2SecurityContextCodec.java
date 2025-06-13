package com.guia747.infrastructure.oauth2;

import java.util.Base64;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DefaultOAuth2SecurityContextCodec implements OAuth2SecurityContextCodec {

    private final ObjectMapper objectMapper;

    public DefaultOAuth2SecurityContextCodec(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String encodeContext(OAuth2SecurityContext context) {
        try {
            String json = objectMapper.writeValueAsString(context);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to encode security context", e);
        }
    }

    @Override
    public OAuth2SecurityContext decodeContext(String encodedContext) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedContext);
            String json = new String(decodedBytes);

            return objectMapper.readValue(json, OAuth2SecurityContext.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode security context", e);
        }
    }
}
