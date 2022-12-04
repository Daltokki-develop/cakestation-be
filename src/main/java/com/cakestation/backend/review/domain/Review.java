package com.cakestation.backend.review.domain;


import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.review.service.dto.UpdateReviewDto;
import com.cakestation.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cake_store_id")
    private CakeStore cakeStore;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>(); // 리뷰 사진 url

    private int cakeNumber; // 케이크 호수

    private String sheetType; // 시트 종류

    private String requestOption; // 추가 옵션

    @Enumerated(value = EnumType.STRING)
    private DesignSatisfaction designSatisfaction; // 만족도

    @Column(nullable = false)
    private int score; // 별점

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewTag> reviewTags = new ArrayList<>();

    private String content; // 하고 싶은 말


    public void update(UpdateReviewDto updateReviewDto) {
        this.cakeNumber = updateReviewDto.getCakeNumber();
        this.sheetType = updateReviewDto.getSheetType();
        this.requestOption = updateReviewDto.getRequestOption();
        this.designSatisfaction = updateReviewDto.getDesignSatisfaction();
        this.score = updateReviewDto.getScore();
        this.content = updateReviewDto.getContent();

        for (String url : updateReviewDto.getImageUrls()) {
            this.addReviewImage(url);
        }
        for (Tag tag : updateReviewDto.getTags()) {
            this.addReviewTag(tag);
        }
    }

    // 리뷰 생성 메서드
    public static Review createReview(User user, CakeStore cakeStore, CreateReviewDto createReviewDto) {

        Review review = Review.builder()
                .writer(user)
                .cakeStore(cakeStore)
                .cakeNumber(createReviewDto.getCakeNumber())
                .sheetType(createReviewDto.getSheetType())
                .requestOption(createReviewDto.getRequestOption())
                .designSatisfaction(createReviewDto.getDesignSatisfaction())
                .score(createReviewDto.getScore())
                .content(createReviewDto.getContent())
                .build();

        for (String url : createReviewDto.getImageUrls()) {
            review.addReviewImage(url);
        }
        for (Tag tag : createReviewDto.getTags()) {
            review.addReviewTag(tag);
        }

        cakeStore.plusReviewCount();
        cakeStore.getReviews().add(review);

        return review;
    }

    // 연관관계 편의 메서드
    public void addReviewTag(Tag tag) {
        ReviewTag reviewTag = new ReviewTag(null, this, tag);
        reviewTags.add(reviewTag);
        reviewTag.setReview(this);
    }

    public void addReviewImage(String url) {
        ReviewImage reviewImage = new ReviewImage(null, this, url);
        reviewImages.add(reviewImage);
    }
}
