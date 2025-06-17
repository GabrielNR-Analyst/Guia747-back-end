package com.guia747.cities.entity;

import java.util.UUID;
import com.guia747.cities.vo.Image;
import com.guia747.common.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class City extends AggregateRoot<UUID> {

    private String name;
    private State state;
    private String slug;
    private String description;
    private String about;
    private Image thumbnail;
    private Image banner;

    public static City createNew(String name, State state, String slug, String description) {
        return new City(name, state, slug, description, null, null, null);
    }

    public void updateImages(Image newThumbnail, Image newBanner) {
        this.thumbnail = newThumbnail;
        this.banner = newBanner;
    }
}
