package com.guia747.application.usecase;

import com.guia747.web.dto.GoogleAuthenticationResponse;

public interface AuthenticateWithGoogleUseCase {

    GoogleAuthenticationResponse execute(String googleIdToken);
}
