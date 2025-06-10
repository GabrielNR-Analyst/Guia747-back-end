package com.guia747.application.usecase;

import com.guia747.application.dto.SocialAuthenticationResponse;

public interface AuthenticateWithSocialProviderUseCase {

    SocialAuthenticationResponse execute(String googleIdToken);
}
