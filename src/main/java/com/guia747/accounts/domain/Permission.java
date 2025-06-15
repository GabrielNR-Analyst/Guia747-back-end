package com.guia747.accounts.domain;

import lombok.Getter;

@Getter
public class Permission {

    private final String name;
    private final String description;

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
