package com.guia747.infrastructure.service;

import java.util.UUID;
import com.guia747.domain.entity.UserAccount;
import com.guia747.domain.vo.TokenPair;

public interface JwtTokenService {

    TokenPair generateTokenPair(UserAccount userAccount);

    boolean validateAccessToken(String accessToken);

    UUID extractAccountIdFromAccessToken(String accessToken);
}
