package com.cakestation.backend.store.domain;

import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import com.cakestation.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 64)
    private String name;

    private String address;

    private String businessHours;

    private String phone;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "store_image", joinColumns =
    @JoinColumn(name = "store_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    private String webpageUrl;

    private String kakaoMapUrl;

    private Double score;

    private Integer numOfPhoto;

    private Integer numOfReviews;

    @Builder.Default
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public static Store createStore(User user, CreateStoreDto createStoreDto) {
        return Store.builder()
                .user(user)
                .name(createStoreDto.getName())
                .address(createStoreDto.getAddress())
                .businessHours(createStoreDto.getBusinessHours())
                .phone(createStoreDto.getPhone())
                .imageUrls(createStoreDto.getImageUrls())
                .webpageUrl(createStoreDto.getWebpageUrl())
                .kakaoMapUrl(createStoreDto.getKakaoMapUrl())
                .score(0.0)
                .numOfPhoto(0)
                .numOfReviews(0)
                .build();
    }
}
