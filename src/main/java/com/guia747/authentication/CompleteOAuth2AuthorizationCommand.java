package com.guia747.authentication;

public record CompleteOAuth2AuthorizationCommand(String providerName, String authorizationCode, String state,
        String securityContextCookieValue) {

}
