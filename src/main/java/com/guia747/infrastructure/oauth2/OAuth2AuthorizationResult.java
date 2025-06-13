package com.guia747.infrastructure.oauth2;

public record OAuth2AuthorizationResult(String authorizationUrl, OAuth2SecurityContext securityContext) {

}
