package com.cakestation.backend.store.controller.dto.request;

import com.cakestation.backend.store.domain.CakeStore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CreateCakeStoreRequest {
    private Long storeId;
    private String name;
    private String address;
    private String businessHours;
    private String phoneNumber;
    private String thumbnail;
    private String webpageUrl;
    private String mapUrl;
    private String nearByStation;

    public static CakeStore toEntity(CreateCakeStoreRequest createStoreDto){
        return CakeStore.builder()
                .name(createStoreDto.getName())
                .address(createStoreDto.getAddress())
                .businessHours(createStoreDto.getBusinessHours())
                .phoneNumber(createStoreDto.getPhoneNumber())
                .thumbnail(createStoreDto.getThumbnail())
                .webpageUrl(createStoreDto.getWebpageUrl())
                .mapUrl(createStoreDto.getMapUrl())
                .nearByStation(createStoreDto.getNearByStation())
                .build();
    }
}
