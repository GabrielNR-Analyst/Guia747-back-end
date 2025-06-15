package com.guia747.accounts.service;

import com.guia747.accounts.domain.UserAccount;
import com.guia747.infrastructure.oauth2.OAuth2UserProfile;

public interface UserAccountService {

    UserAccount findOrCreateFromOAuth2(OAuth2UserProfile oauth2User);
}
