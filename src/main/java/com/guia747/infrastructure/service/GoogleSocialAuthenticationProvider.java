package com.guia747.infrastructure.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.guia747.domain.exception.GoogleUserInfoUnavailableException;
import com.guia747.domain.exception.InvalidGoogleTokenException;
import com.guia747.domain.service.SocialAuthenticationProvider;
import com.guia747.domain.vo.SocialAuthenticationToken;
import com.guia747.domain.vo.SocialUserProfile;
import com.guia747.infrastructure.config.properties.AppProperties;
import com.guia747.infrastructure.exception.GoogleTokenProcessingException;

@Service
public class GoogleSocialAuthenticationProvider implements SocialAuthenticationProvider {

    private final GoogleIdTokenVerifier verifier;

    public GoogleSocialAuthenticationProvider(AppProperties appProperties) {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(appProperties.social().google().clientId()))
                .build();
    }

    @Override
    public SocialUserProfile validateTokenAndExtractProfile(SocialAuthenticationToken token) {
        try {
            GoogleIdToken idToken = verifier.verify(token.value());
            if (idToken == null) {
                throw new InvalidGoogleTokenException();
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Extract user information
            String googleUserId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            // Validate required fields
            if (googleUserId == null || email == null || name == null) {
                throw new GoogleUserInfoUnavailableException(
                        "Required user information not available in Google token"
                );
            }

            return new SocialUserProfile(googleUserId, email, name, pictureUrl);
        } catch (GeneralSecurityException | IOException e) {
            throw new GoogleTokenProcessingException("Error processing Google token", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidGoogleTokenException();
        }
    }
}
