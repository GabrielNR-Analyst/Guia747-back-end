package com.guia747.application.users;

import com.guia747.domain.users.entity.User;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;

public interface UserService {

    User findOrCreateFromOAuth2(OAuth2UserProfile oauth2User);
}
