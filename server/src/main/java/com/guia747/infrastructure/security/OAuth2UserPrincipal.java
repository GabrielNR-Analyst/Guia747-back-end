package com.guia747.infrastructure.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.guia747.accounts.domain.UserAccount;

public class OAuth2UserPrincipal implements UserDetails {

    private final UserAccount userAccount;

    public OAuth2UserPrincipal(UserAccount userAccount) {
        this.userAccount = userAccount;
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
        return userAccount.getEmail();
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
