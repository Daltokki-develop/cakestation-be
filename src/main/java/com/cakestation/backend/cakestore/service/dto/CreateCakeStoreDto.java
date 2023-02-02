package com.cakestation.backend.cakestore.service.dto;

import com.cakestation.backend.cakestore.domain.CakeStore;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CreateCakeStoreDto {
    private Long storeId;
    private String name;
    private String address;
    private String businessHours;
    private String phoneNumber;
    private String thumbnail;
    private String webpageUrl;
    private String mapUrl;
    private String nearByStation;

    public static CreateCakeStoreDto from(CakeStore store) {

        return CreateCakeStoreDto.builder()
                .storeId(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .businessHours(store.getBusinessHours())
                .phoneNumber(store.getPhoneNumber())
                .thumbnail(store.getThumbnail())
                .webpageUrl(store.getWebpageUrl())
                .mapUrl(store.getMapUrl())
                .nearByStation(store.getNearByStation())
                .build();
    }
}
