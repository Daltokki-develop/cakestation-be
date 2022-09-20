package com.cakestation.backend.store.dto.response;

import com.cakestation.backend.store.domain.Store;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class StoreResponse {
    private String name;

    private String address;

    private String businessHours;

    private String phone;

    private String photoUrl;

    private String webpageUrl;

    private String kakaoMapUrl;

    private Double score;

    public static StoreResponse from(Store store){
        return StoreResponse.builder()
                .name(store.getName())
                .address(store.getAddress())
                .businessHours(store.getBusinessHours())
                .phone(store.getPhone())
                .photoUrl(store.getPhotoUrl())
                .webpageUrl(store.getWebpageUrl())
                .kakaoMapUrl(store.getKakaoMapUrl())
                .build();
    }
}
