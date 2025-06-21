package com.guia747.application.authentication.usecase;

import com.guia747.application.authentication.command.AuthenticateWithOAuth2Command;
import com.guia747.web.dtos.authentication.AuthenticationResponse;

public interface AuthenticateWithOAuth2UseCase {

    AuthenticationResponse execute(AuthenticateWithOAuth2Command command);
}
