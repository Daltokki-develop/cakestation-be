package com.cakestation.backend.review.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Enumerated(value = EnumType.STRING)
    private Tag tag;

    public ReviewTag(Review review, Tag tag) {
        this.id = null;
        this.review = review;
        this.tag = tag;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
