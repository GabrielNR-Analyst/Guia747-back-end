package com.guia747.infrastructure.security;

import jakarta.servlet.http.HttpServletResponse;

public interface SecureRefreshTokenCookieService {

    void setRefreshTokenCookie(HttpServletResponse response, String refreshToken, long maxAgeSeconds);
}
