package com.guia747.authentication.command;

public record OAuth2AuthenticationCommand(String authorizationCode, String ipAddress, String userAgent) {

}
