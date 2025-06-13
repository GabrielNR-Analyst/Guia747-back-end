package com.guia747.infrastructure.oauth2;

public interface OAuth2SecurityContextCodec {

    String encodeContext(OAuth2SecurityContext context);

    OAuth2SecurityContext decodeContext(String encodedContext);
}
