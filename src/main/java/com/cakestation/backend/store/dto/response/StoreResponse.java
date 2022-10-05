package com.cakestation.backend.store.dto.response;

import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.service.StoreDto;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class StoreResponse {
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

    public static StoreResponse from(StoreDto storeDto){
        return StoreResponse.builder()
                .storeId(storeDto.getStoreId())
                .name(storeDto.getName())
                .address(storeDto.getAddress())
                .businessHours(storeDto.getBusinessHours())
                .phone(storeDto.getPhone())
                .imageUrls(storeDto.getImageUrls())
                .webpageUrl(storeDto.getWebpageUrl())
                .kakaoMapUrl(storeDto.getKakaoMapUrl())
                .reviewNum(storeDto.getReviewNum())
                .build();
    }
}
