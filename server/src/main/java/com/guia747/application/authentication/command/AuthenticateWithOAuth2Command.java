package com.guia747.application.authentication.command;

public record AuthenticateWithOAuth2Command(String authorizationCode, String ipAddress, String userAgent) {

}
