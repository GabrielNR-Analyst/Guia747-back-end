package com.guia747.application.authentication.usecase;

public interface RevokeRefreshTokenUseCase {

    void execute(String refreshToken);
}
