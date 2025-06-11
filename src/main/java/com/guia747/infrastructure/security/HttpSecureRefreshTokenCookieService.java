package com.guia747.infrastructure.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.guia747.infrastructure.config.properties.AppProperties;

@Component
public class HttpSecureRefreshTokenCookieService implements SecureRefreshTokenCookieService {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "_rt";

    private final AppProperties appProperties;

    public HttpSecureRefreshTokenCookieService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken, long maxAgeSeconds) {
        // Set refresh token cookie
        var tokenCookie = createSecureCookie(refreshToken, maxAgeSeconds);
        response.addCookie(tokenCookie);
    }

    @Override
    public void clearRefreshTokenCookie(HttpServletResponse response) {
        var tokenCookie = createSecureCookie("", 0);
        response.addCookie(tokenCookie);
    }

    private Cookie createSecureCookie(String value, long maxAgeSeconds) {
        var cookie = new Cookie(HttpSecureRefreshTokenCookieService.REFRESH_TOKEN_COOKIE_NAME, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(appProperties.security().cookie().secure());
        cookie.setMaxAge((int) maxAgeSeconds);
        cookie.setPath("/");
        return cookie;
    }
}
