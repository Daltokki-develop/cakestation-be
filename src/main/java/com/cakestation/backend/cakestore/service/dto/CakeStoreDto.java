package com.cakestation.backend.cakestore.service.dto;

import com.cakestation.backend.cakestore.domain.CakeStore;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CakeStoreDto {
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

    public static CakeStoreDto from(CakeStore store, List<String> reviewImageUrls) {

        List<String> storeImages = new ArrayList<>();
        if (store.getThumbnail() != null) {
            storeImages.add(store.getThumbnail());
        }
        storeImages.addAll(reviewImageUrls);

        return CakeStoreDto.builder()
                .storeId(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .businessHours(store.getBusinessHours())
                .phoneNumber(store.getPhoneNumber())
                .thumbnail(store.getThumbnail())
                .webpageUrl(store.getWebpageUrl())
                .mapUrl(store.getMapUrl())
                .nearByStation(store.getNearByStation())
                .storeImages(storeImages)
                .build();
    }
}
