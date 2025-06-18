package com.guia747.places.entity;

import java.util.UUID;
import com.guia747.common.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category extends AggregateRoot<UUID> {

    private String name;
    private String slug;
    private String description;
}
