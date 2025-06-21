package com.guia747.infrastructure.security;

import com.guia747.domain.users.entity.User;

public interface JwtTokenService {

    JwtTokenPair generateTokenPair(User user);
}
