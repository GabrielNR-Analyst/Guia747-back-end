package com.guia747.authentication.command;

public record RefreshAccessTokenCommand(String refreshToken, String ipAddress, String userAgent) {

}
