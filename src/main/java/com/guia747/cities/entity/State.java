package com.guia747.cities.entity;

import java.util.UUID;
import com.guia747.common.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class State extends AggregateRoot<UUID> {

    private String name;
    private String uf;
}
