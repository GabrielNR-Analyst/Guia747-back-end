package com.guia747.authentication.usecase;

public interface RevokeRefreshTokenUseCase {

    void execute(String refreshToken);
}
