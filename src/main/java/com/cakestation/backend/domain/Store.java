package com.cakestation.backend.domain;

import com.cakestation.backend.service.dto.request.CreateStoreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 64)
    private String name;

    private String address;

    private String businessHours;

    private String phone;

    private String photoUrl;

    private String webpageUrl;

    private String kakaoMapUrl;

    private Double score;

    private Integer numOfPhoto;

    private Integer numOfReviews;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;

    public static Store createStore(User user, CreateStoreDto createStoreDto){
        return Store.builder()
                .user(user)
                .name(createStoreDto.getName())
                .address(createStoreDto.getAddress())
                .businessHours(createStoreDto.getBusinessHours())
                .phone(createStoreDto.getPhone())
                .photoUrl(createStoreDto.getPhotoUrl())
                .webpageUrl(createStoreDto.getWebpageUrl())
                .kakaoMapUrl(createStoreDto.getKakaoMapUrl())
                .score(0.0)
                .numOfPhoto(0)
                .numOfReviews(0)
                .uploadDate(new Date())
                .build();
        }

}
