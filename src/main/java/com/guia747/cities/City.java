package com.guia747.cities;

import java.util.UUID;
import lombok.Getter;

@Getter
public class City {

    private final UUID id;

    private final String name;
    private final String state;
    private final String slug;
    private final String about;
    private final String description;
    private final String thumbnailUrl;
    private final String bannerUrl;

    public City(UUID id, String name, String state, String slug, String about, String description, String thumbnailUrl,
            String bannerUrl) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.slug = slug;
        this.about = about;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.bannerUrl = bannerUrl;
    }
}
