package com.guia747.infrastructure.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.guia747.accounts.domain.UserAccount;
import lombok.Getter;

public class OAuth2UserPrincipal implements UserDetails {

    @Getter
    private final UserAccount userAccount;
    private final List<GrantedAuthority> authorities;

    public OAuth2UserPrincipal(UserAccount userAccount) {
        this.userAccount = userAccount;
        this.authorities = userAccount.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE" + role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userAccount.getEmail();
    }
}
