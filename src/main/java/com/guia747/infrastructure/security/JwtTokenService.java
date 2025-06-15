package com.guia747.infrastructure.security;

import com.guia747.accounts.domain.UserAccount;

public interface JwtTokenService {

    JwtToken generateAccessToken(UserAccount userAccount);
}
