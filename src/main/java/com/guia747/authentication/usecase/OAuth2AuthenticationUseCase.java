package com.guia747.authentication.usecase;

import com.guia747.authentication.OAuth2AuthenticationRequest;

public interface OAuth2AuthenticationUseCase {

    void execute(OAuth2AuthenticationRequest request);
}
