package com.guia747.infrastructure.oauth2;

public interface OAuth2UserService {

    OAuth2UserProfile getUserProfile(String accessToken);
}
