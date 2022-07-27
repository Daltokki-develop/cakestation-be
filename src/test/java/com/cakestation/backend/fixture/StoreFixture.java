package com.cakestation.backend.fixture;

import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.cakestation.backend.fixture.UserFixture.getUser;

public class StoreFixture {

    public static Long id = 1L;
    public static String name = "베니케이크";
    public static String address = "서울 마포구 와우산로29다길 11";
    public static String businessHours = "화~토 12:00 ~ 20:00";
    public static String phone = "010-2222-3333";
    public static String photoUrl = "~~";
    public static String webpageUrl = "https://www.instagram.com/benny.cake/";
    public static String kakaoMapUrl = "https://place.map.kakao.com/1272936699";
    public static Double score = 0.0;
    public static Integer numOfPhoto = 0;
    public static Integer numOfReviews = 0;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public static Date uploadDate = new Date();

    public static CreateStoreDto getStoreDto() {
        return CreateStoreDto.builder()
                .name(name)
                .address(address)
                .businessHours(businessHours)
                .phone(phone)
                .photoUrl(photoUrl)
                .webpageUrl(webpageUrl)
                .kakaoMapUrl(kakaoMapUrl)
                .score(score)
                .build();
    }

    public static Store getStore() {
        return Store.builder()
                .id(1L)
                .user(getUser())
                .name(name)
                .address(address)
                .businessHours(businessHours)
                .phone(phone)
                .photoUrl(photoUrl)
                .webpageUrl(webpageUrl)
                .kakaoMapUrl(kakaoMapUrl)
                .score(score)
                .uploadDate(new Date())
                .numOfReviews(0)
                .numOfPhoto(0)
                .uploadDate(uploadDate)
                .build();
    }
}
