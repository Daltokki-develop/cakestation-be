package com.cakestation.backend.cakestore.domain;


import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.review.domain.Review;
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
public class CakeStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cake_store_id")
    private Long id;

    private String name;

    private String address;

    private String businessHours;

    private String phoneNumber;

    private String thumbnail;

    private String webpageUrl;

    private String mapUrl;

    private String nearByStation;

    @Builder.Default
    @OneToMany(mappedBy = "cakeStore")
    private List<Review> reviews = new ArrayList<>();
    private int reviewCount = 0;

    private double reviewScore = 0.0;

    public static CakeStore createCakeStore(CreateCakeStoreDto createCakeStoreDto) {
        return CakeStore.builder()
                .name(createCakeStoreDto.getName())
                .address(createCakeStoreDto.getAddress())
                .businessHours(createCakeStoreDto.getBusinessHours())
                .phoneNumber(createCakeStoreDto.getPhoneNumber())
                .thumbnail(createCakeStoreDto.getThumbnail())
                .webpageUrl(createCakeStoreDto.getWebpageUrl())
                .mapUrl(createCakeStoreDto.getMapUrl())
                .nearByStation(createCakeStoreDto.getNearByStation())
                .reviewCount(0)
                .reviewScore(0.0)
                .build();
    }

    public void applyReview(double reviewScore) {
        addReviewCount();
        addReviewScore(reviewScore);
    }

    public void addReviewCount() {
        this.reviewCount++;
    }

    public void addReviewScore(double reviewScore) {
        double totalSum = this.reviewScore * (this.reviewCount - 1);
        totalSum += reviewScore;
        this.reviewScore = totalSum / this.reviewCount;
    }
}
