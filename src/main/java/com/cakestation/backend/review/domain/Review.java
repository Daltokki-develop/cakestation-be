package com.cakestation.backend.review.domain;


import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.store.domain.Store;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String nearByStation; // 가장 가까운 역

    @Enumerated(value = EnumType.STRING)
    private Distance walkingDistance; // 도보 거리

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "review_image", joinColumns =
    @JoinColumn(name = "review_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>(); // 리뷰 사진 url

    private int cakeNumber; // 케이크 호수

    private String sheetType; // 시트 종류

    private String requestOption; // 추가 옵션

    @Enumerated(value = EnumType.STRING)
    private DesignSatisfaction designSatisfaction; // 만족도

    @Column(nullable = false)
    private int score; // 별점

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewTag> tags = new ArrayList<>();

    private String content; // 하고 싶은 말

    // 리뷰 생성 메서드
    public static Review createReview(User user, Store store, CreateReviewDto createReviewDto){

        Review review = Review.builder()
                .writer(user)
                .store(store)
                .nearByStation(createReviewDto.getNearByStation())
                .walkingDistance(createReviewDto.getWalkingDistance())
                .imageUrls(createReviewDto.getImageUrls())
                .cakeNumber(createReviewDto.getCakeNumber())
                .sheetType(createReviewDto.getSheetType())
                .requestOption(createReviewDto.getRequestOption())
                .designSatisfaction(createReviewDto.getDesignSatisfaction())
                .score(createReviewDto.getScore())
                .content(createReviewDto.getContent())
                .build();

        for (Tag tag: createReviewDto.getTags()){
            review.addTag(tag);
        }
        return review;
    }

    // 연관관계 편의 메서드
    public void addTag(Tag tag){
        ReviewTag reviewTag = ReviewTag.builder()
                .tag(tag)
                .build();
        tags.add(reviewTag);
        reviewTag.setReview(this);
    }
}
