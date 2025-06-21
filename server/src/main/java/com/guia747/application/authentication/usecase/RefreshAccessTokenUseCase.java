package com.guia747.application.authentication.usecase;

import com.guia747.application.authentication.command.RefreshAccessTokenCommand;
import com.guia747.web.dtos.authentication.AuthenticationResponse;

public interface RefreshAccessTokenUseCase {

    AuthenticationResponse execute(RefreshAccessTokenCommand command);
}
