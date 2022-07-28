package com.cakestation.backend.review.domain;

import javax.persistence.*;

@Entity
public class ReviewTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Tag tag;

}
