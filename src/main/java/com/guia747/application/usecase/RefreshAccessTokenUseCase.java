package com.guia747.application.usecase;

import com.guia747.domain.vo.TokenPair;

public interface RefreshAccessTokenUseCase {

    TokenPair execute(String refreshToken);
}
