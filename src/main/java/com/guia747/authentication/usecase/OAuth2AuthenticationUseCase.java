package com.guia747.authentication.usecase;

import com.guia747.accounts.UserAccount;
import com.guia747.authentication.OAuth2AuthenticationRequest;

public interface OAuth2AuthenticationUseCase {

    UserAccount execute(OAuth2AuthenticationRequest request);
}
