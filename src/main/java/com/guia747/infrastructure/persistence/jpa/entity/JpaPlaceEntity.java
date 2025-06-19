package com.guia747.infrastructure.persistence.jpa.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.guia747.infrastructure.persistence.jpa.embeddable.FAQEmbeddable;
import com.guia747.infrastructure.persistence.jpa.embeddable.OperatingHoursEmbeddable;
import com.guia747.infrastructure.persistence.jpa.embeddable.PlaceAddressEmbeddable;
import com.guia747.infrastructure.persistence.jpa.embeddable.PlaceContactEmbeddable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "places", indexes = {
        @Index(name = "idx_place_user_id", columnList = "user_id"),
        @Index(name = "idx_place_city_id", columnList = "city_id"),
        @Index(name = "idx_place_name", columnList = "name"),
})
@Getter
@Setter
public class JpaPlaceEntity extends JpaAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private JpaCityEntity city;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "about", length = 500)
    private String about;

    @Embedded
    private PlaceAddressEmbeddable address;

    @Embedded
    private PlaceContactEmbeddable contact;

    @Column(name = "youtube_video_url", length = 500)
    private String youtubeVideoUrl;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "place_operating_hours", joinColumns = @JoinColumn(name = "place_id"))
    private List<OperatingHoursEmbeddable> operatingHours;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "place_categories",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<JpaCategoryEntity> categories = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "place_faqs", joinColumns = @JoinColumn(name = "place_id"))
    private List<FAQEmbeddable> faqs;
}
