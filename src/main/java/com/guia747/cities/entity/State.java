package com.guia747.cities.entity;

import java.util.UUID;
import com.guia747.common.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class State extends AggregateRoot<UUID> {

    private String name;
    private String uf;
}
