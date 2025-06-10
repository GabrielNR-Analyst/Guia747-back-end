package com.guia747.application.usecase;

import com.guia747.application.dto.SocialAuthenticationResult;

public interface AuthenticateWithSocialProviderUseCase {

    SocialAuthenticationResult execute(String googleIdToken);
}
