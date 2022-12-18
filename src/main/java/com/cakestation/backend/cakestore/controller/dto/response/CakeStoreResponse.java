package com.cakestation.backend.cakestore.controller.dto.response;

import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CakeStoreResponse {
    private Long storeId;
    private String name;
    private String address;
    private String businessHours;
    private String phoneNumber;
    private String thumbnail;
    private String webpageUrl;
    private String mapUrl;
    private String nearByStation;
    private List<String> storeImages;
    private int reviewCount;
    private double reviewScore;

    public static CakeStoreResponse from(CakeStoreDto storeDto) {
        return CakeStoreResponse.builder()
                .storeId(storeDto.getStoreId())
                .name(storeDto.getName())
                .address(storeDto.getAddress())
                .businessHours(storeDto.getBusinessHours())
                .phoneNumber(storeDto.getPhoneNumber())
                .thumbnail(storeDto.getThumbnail())
                .webpageUrl(storeDto.getWebpageUrl())
                .mapUrl(storeDto.getMapUrl())
                .nearByStation(storeDto.getNearByStation())
                .storeImages(storeDto.getStoreImages())
                .reviewCount(storeDto.getReviewCount())
                .reviewScore(storeDto.getReviewScore())
                .build();
    }
}
