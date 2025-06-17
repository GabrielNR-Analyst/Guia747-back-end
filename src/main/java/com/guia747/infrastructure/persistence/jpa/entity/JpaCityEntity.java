package com.guia747.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cities", indexes = {
        @Index(name = "idx_city_name", columnList = "name"),
        @Index(name = "idx_city_slug", columnList = "slug"),
        @Index(name = "idx_city_state_id", columnList = "state_id"),
        @Index(name = "idx_city_name_state_id", columnList = "name, state_id")
})
@Getter
@Setter
public class JpaCityEntity extends JpaAuditableEntity {

    // Cidade com maior nome tem 32 caracteres
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private JpaStateEntity state;

    @Column(name = "slug", nullable = false, unique = true, length = 50)
    private String slug;

    @Column(name = "description", nullable = false, length = 320)
    private String description;

    @Column(name = "about", length = 2000)
    private String about;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(name = "thumbnail_width")
    private Integer thumbnailWidth;

    @Column(name = "thumbnail_height")
    private Integer thumbnailHeight;

    @Column(name = "banner_url", length = 500)
    private String bannerUrl;

    @Column(name = "banner_width")
    private Integer bannerWidth;

    @Column(name = "banner_height")
    private Integer bannerHeight;
}
