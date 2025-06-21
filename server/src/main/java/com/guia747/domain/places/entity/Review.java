package com.guia747.domain.places.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.guia747.domain.users.entity.User;
import com.guia747.shared.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Review extends AggregateRoot<UUID> {

    private Place place;
    private User reviewer;
    private int rating;
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Review createNew(Place place, User reviewer, int rating, String comment) {
        return new Review(place, reviewer, rating, comment, null, null);
    }
}
