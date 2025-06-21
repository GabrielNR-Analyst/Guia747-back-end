package com.guia747.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "place_reviews", indexes = {
        @Index(name = "idx_review_place_id", columnList = "place_id"),
        @Index(name = "idx_review_reviewer_id", columnList = "reviewer_id"),
        @Index(name = "idx_review_place_reviewer", columnList = "place_id,reviewer_id", unique = true),
        @Index(name = "idx_review_rating", columnList = "rating"),
        @Index(name = "idx_review_created_at", columnList = "created_at")
})
@Getter
@Setter
public class JpaReviewEntity extends JpaAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private JpaPlaceEntity place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private UserJpaEntity reviewer;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 1000)
    private String comment;
}
