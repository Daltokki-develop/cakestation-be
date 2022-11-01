package com.cakestation.backend.cakestore.domain;


import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CakeStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cake_store_id")
    private Long id;

    private String name;

    private String address;

    private String businessHours;

    private String phoneNumber;

    private String thumbnail;

    private String webpageUrl;

    private String mapUrl;

    private String nearByStation;

    public static CakeStore createCakeStore(CreateCakeStoreDto createCakeStoreDto){
        return CakeStore.builder()
                .name(createCakeStoreDto.getName())
                .address(createCakeStoreDto.getAddress())
                .businessHours(createCakeStoreDto.getBusinessHours())
                .phoneNumber(createCakeStoreDto.getPhoneNumber())
                .thumbnail(createCakeStoreDto.getThumbnail())
                .webpageUrl(createCakeStoreDto.getWebpageUrl())
                .mapUrl(createCakeStoreDto.getMapUrl())
                .nearByStation(createCakeStoreDto.getNearByStation())
                .build();
    }
}
