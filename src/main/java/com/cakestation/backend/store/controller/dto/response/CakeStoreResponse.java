package com.cakestation.backend.store.controller.dto.response;

import com.cakestation.backend.store.service.dto.CakeStoreDto;
import com.cakestation.backend.store.service.dto.CreateCakeStoreDto;
import lombok.*;

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

    public static CakeStoreResponse from(CakeStoreDto storeDto){
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
                .build();
    }
}
