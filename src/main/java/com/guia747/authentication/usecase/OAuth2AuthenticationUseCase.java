package com.guia747.authentication.usecase;

import com.guia747.authentication.command.OAuth2AuthenticationCommand;
import com.guia747.authentication.dto.AuthenticationResponse;

public interface OAuth2AuthenticationUseCase {

    AuthenticationResponse execute(OAuth2AuthenticationCommand command);
}
