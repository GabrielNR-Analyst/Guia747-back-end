package com.guia747.accounts.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class Role {

    private final String name;
    private final String description;

    private final Set<Permission> permissions;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
        this.permissions = new HashSet<>();
    }
}
