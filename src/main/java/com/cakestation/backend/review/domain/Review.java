package com.cakestation.backend.review.domain;


import com.cakestation.backend.review.dto.request.CreateReviewDto;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(value = EnumType.ORDINAL)
    private Distance walkingDistance; // 도보 거리

    private String photoUrl; // 리뷰 사진 url

    private int cakeNumber; // 케이크 호수

    private String sheetType; // 시트 종류

    private String requestOption; // 추가 옵션

    private int satisfaction; // 만족도

    private int score; // 별점

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewTag> tags = new ArrayList<>();

    private String content; // 내용

    private LocalDateTime createdAt;

    // 리뷰 생성 메서드
    public static Review createReview(User user, Store store, CreateReviewDto createReviewDto){

        Review review = Review.builder()
                .writer(user)
                .store(store)
                .walkingDistance(createReviewDto.getWalkingDistance())
                .photoUrl(createReviewDto.getPhotoUrl())
                .cakeNumber(createReviewDto.getCakeNumber())
                .sheetType(createReviewDto.getSheetType())
                .requestOption(createReviewDto.getRequestOption())
                .satisfaction(createReviewDto.getSatisfaction())
                .score(createReviewDto.getScore())
                .content(createReviewDto.getContent())
                .createdAt(LocalDateTime.now())
                .tags(new ArrayList<>())
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
