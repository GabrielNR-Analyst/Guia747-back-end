package com.guia747.application.usecase;

import com.guia747.application.dto.GoogleAuthenticationResult;

public interface AuthenticateWithGoogleUseCase {

    GoogleAuthenticationResult execute(String googleIdToken);
}
