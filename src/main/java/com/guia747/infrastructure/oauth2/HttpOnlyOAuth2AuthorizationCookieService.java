package com.guia747.infrastructure.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class HttpOnlyOAuth2AuthorizationCookieService implements OAuth2AuthorizationCookieService {

    private static final String COOKIE_NAME = "oauth2_sec";
    private static final int COOKIE_MAX_AGE = 900;

    private final OAuth2SecurityContextCodec securityContextCodec;

    public HttpOnlyOAuth2AuthorizationCookieService(OAuth2SecurityContextCodec securityContextCodec) {
        this.securityContextCodec = securityContextCodec;
    }

    @Override
    public void setAuthorizationCookie(HttpServletResponse response, OAuth2SecurityContext securityContext) {
        String encodedContext = securityContextCodec.encodeContext(securityContext);
        Cookie cookie = new Cookie(COOKIE_NAME, encodedContext);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);
    }
}
