package com.guia747.authentication.usecase;

import com.guia747.authentication.dto.AuthenticationResponse;
import com.guia747.authentication.dto.OAuth2AuthenticationRequest;

public interface OAuth2AuthenticationUseCase {

    AuthenticationResponse execute(OAuth2AuthenticationRequest request);
}
