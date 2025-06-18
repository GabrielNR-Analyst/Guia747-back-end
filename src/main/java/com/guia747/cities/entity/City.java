package com.guia747.cities.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.guia747.cities.vo.Image;
import com.guia747.common.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class City extends AggregateRoot<UUID> {

    private String name;
    private State state;
    private String slug;
    private String description;
    private String about;
    private Image thumbnail;
    private Image banner;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static City createNew(String name, State state, String slug, String description) {
        return new City(name, state, slug, description, null, null, null, null, null);
    }

    public void updateDetails(String description, String about) {
        if (description != null && !description.isBlank() && !description.equals(this.description)) {
            this.description = description;
        }
        if (about != null && !about.isBlank() && !about.equals(this.about)) {
            this.about = about;
        }
    }

    public void updateImages(Image newThumbnail, Image newBanner) {
        if (newThumbnail != null) {
            this.thumbnail = newThumbnail;
        }
        if (newBanner != null) {
            this.banner = newBanner;
        }
    }
}
