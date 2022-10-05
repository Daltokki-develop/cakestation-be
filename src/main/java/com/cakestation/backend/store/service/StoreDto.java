package com.cakestation.backend.store.service;

import com.cakestation.backend.store.domain.Store;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class StoreDto {
    private Long storeId;
    private String name;
    private String address;
    private String businessHours;
    private String phone;
    private List<String> imageUrls;
    private String webpageUrl;
    private String kakaoMapUrl;
    private Double score;
    private int reviewNum;

    public static StoreDto from(Store store) {

        return StoreDto.builder()
                .storeId(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .businessHours(store.getBusinessHours())
                .phone(store.getPhone())
                .imageUrls(store.getImageUrls())
                .webpageUrl(store.getWebpageUrl())
                .kakaoMapUrl(store.getKakaoMapUrl())
                .reviewNum(store.getReviews().size())
                .build();
    }
}
