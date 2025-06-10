package com.guia747.domain.service;

import com.guia747.domain.vo.SocialAuthenticationToken;
import com.guia747.domain.vo.SocialUserProfile;

public interface SocialAuthenticationProvider {

    SocialUserProfile validateTokenAndExtractProfile(SocialAuthenticationToken token);
}
