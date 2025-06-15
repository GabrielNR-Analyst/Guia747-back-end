package com.guia747.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cities", indexes = {
        @Index(name = "idx_city_slug", columnList = "slug", unique = true),
        @Index(name = "idx_city_state", columnList = "state"),
        @Index(name = "idx_city_name", columnList = "name"),
        @Index(name = "idx_city_name_state", columnList = "name, state", unique = true)
})
@Getter
@Setter
public class CityJpaEntity extends JpaAuditableEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "about", nullable = false, length = 320)
    private String about;

    @Column(name = "description", length = 320)
    private String description;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "banner_url", length = 500)
    private String bannerUrl;
}
