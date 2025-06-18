package com.guia747.places.entity;

import java.time.LocalDateTime;
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Category createNew(String name, String slug, String description) {
        return new Category(name, slug, description, null, null);
    }
}
