package com.guia747.infrastructure.oauth2;

import jakarta.servlet.http.HttpServletResponse;

public interface OAuth2AuthorizationCookieService {

    void setAuthorizationCookie(HttpServletResponse response, OAuth2SecurityContext securityContext);
}
