package com.guia747.infrastructure.oauth2;

public record OAuth2SecurityContext(String pkceVerifier, String state, String providerName) {

}
