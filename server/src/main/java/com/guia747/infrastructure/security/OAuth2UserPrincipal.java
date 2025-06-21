package com.guia747.infrastructure.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.guia747.domain.users.entity.User;

public class OAuth2UserPrincipal implements UserDetails {

    private final User user;

    public OAuth2UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public User getUserAccount() {
        return user;
    }
}
