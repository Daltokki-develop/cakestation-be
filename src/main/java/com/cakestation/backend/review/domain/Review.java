package com.cakestation.backend.review.domain;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.common.domain.BaseEntity;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.UpdateReviewDto;
import com.cakestation.backend.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>(); // 리뷰 사진 url

    private int cakeNumber;

    private String sheetType;

    private String requestOption;

    @Enumerated(value = EnumType.STRING)
    private DesignSatisfaction designSatisfaction;

    @Column(nullable = false)
    private int score;

    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReviewTag> reviewTags = new ArrayList<>();

    private String content;

    @Builder
    public Review(User writer, CakeStore cakeStore, int cakeNumber, String sheetType, String requestOption, DesignSatisfaction designSatisfaction, int score, String content) {
        this.id = null;
        this.writer = writer;
        this.cakeStore = cakeStore;
        this.cakeNumber = cakeNumber;
        this.sheetType = sheetType;
        this.requestOption = requestOption;
        this.designSatisfaction = designSatisfaction;
        this.score = score;
        this.content = content;
        this.reviewImages = new ArrayList<>();
        this.reviewTags = new ArrayList<>();
    }

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

        cakeStore.getReviews().add(review);
        cakeStore.applyReview(createReviewDto.getScore());

        return review;
    }

    public void addReviewTag(Tag tag) {
        ReviewTag reviewTag = new ReviewTag(this, tag);
        reviewTags.add(reviewTag);
    }

    public void addReviewImage(String url) {
        ReviewImage reviewImage = new ReviewImage(this, url);
        reviewImages.add(reviewImage);
    }
}
