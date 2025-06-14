package com.guia747.accounts;

import com.guia747.authentication.OAuth2UserProfile;

public interface UserAccountService {

    UserAccount findOrCreateFromOAuth2(OAuth2UserProfile oauth2User);
}
