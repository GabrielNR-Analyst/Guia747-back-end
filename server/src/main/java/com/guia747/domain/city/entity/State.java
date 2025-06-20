package com.guia747.domain.city.entity;

import java.util.UUID;
import com.guia747.shared.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class State extends AggregateRoot<UUID> {

    private String name;
    private String uf;
}
