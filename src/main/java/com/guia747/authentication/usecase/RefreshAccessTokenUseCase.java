package com.guia747.authentication.usecase;

import com.guia747.authentication.command.RefreshAccessTokenCommand;
import com.guia747.authentication.dto.AuthenticationResponse;

public interface RefreshAccessTokenUseCase {

    AuthenticationResponse execute(RefreshAccessTokenCommand command);
}
