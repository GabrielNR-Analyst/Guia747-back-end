package com.guia747.infrastructure.security;

import com.guia747.accounts.domain.UserAccount;

public interface JwtTokenService {

    JwtTokenPair generateTokenPair(UserAccount userAccount);
}
