package com.guia747.infrastructure.security;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OAuth2AuthenticationToken extends AbstractAuthenticationToken {

    private final OAuth2UserPrincipal principal;

    public OAuth2AuthenticationToken(OAuth2UserPrincipal principal,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
