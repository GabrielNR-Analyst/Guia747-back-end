package com.guia747.application.authentication.command;

public record RefreshAccessTokenCommand(String refreshToken, String ipAddress, String userAgent) {

}
