package com.guia747.infrastructure.oauth2;

import com.guia747.authentication.OAuth2UserProfile;

public interface OAuth2UserService {

    OAuth2UserProfile getUserProfile(String accessToken);
}
