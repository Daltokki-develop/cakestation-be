package com.cakestation.backend.store.dto.request;

import com.cakestation.backend.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CreateStoreDto {
    private String name;

    private String address;

    private String businessHours;

    private String phone;

    private List<String> imageUrls;

    private String webpageUrl;

    private String kakaoMapUrl;

    private Double score;

    public static Store toEntity(CreateStoreDto createStoreDto){
        return Store.builder()
                .name(createStoreDto.getName())
                .address(createStoreDto.getAddress())
                .businessHours(createStoreDto.getBusinessHours())
                .phone(createStoreDto.getPhone())
                .imageUrls(createStoreDto.getImageUrls())
                .webpageUrl(createStoreDto.getWebpageUrl())
                .kakaoMapUrl(createStoreDto.getKakaoMapUrl())
                .build();
    }
}
