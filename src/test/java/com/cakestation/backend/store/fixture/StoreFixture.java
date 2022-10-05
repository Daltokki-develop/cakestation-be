package com.cakestation.backend.store.fixture;

import com.cakestation.backend.store.domain.Menu;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import com.cakestation.backend.user.domain.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class StoreFixture {

    public static Long STORE_ID = 1L;

    public static String NAME = "베니 케이크";

    public static String ADDRESS = "주소";

    public static String BUSINESS_HOURS = "월~금";

    public static String PHONE = "010-1111-2222";

    public static List<String> IMAGE_URLS = List.of("aa","bb");

    public static String WEBPAGE_URL = "sflsdkfjlsdf";

    public static String KAKAOMAP_URL = "sdflsdfnlsdkf";

    public static Double SCORE = 5.0;

    public static Integer NUM_OF_PHOTO = 0;

    public static Integer NUM_OF_REVIEWS = 0;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public static Date UPLOAD_DATE = new Date();

    public static Store storeEntity(){
        return Store.builder()
                .id(STORE_ID)
                .name(NAME)
                .address(ADDRESS)
                .businessHours(BUSINESS_HOURS)
                .phone(PHONE)
                .imageUrls(IMAGE_URLS)
                .webpageUrl(WEBPAGE_URL)
                .kakaoMapUrl(KAKAOMAP_URL)
                .score(SCORE)
                .numOfPhoto(NUM_OF_PHOTO)
                .numOfReviews(NUM_OF_REVIEWS)
                .build();
    }

    public static CreateStoreDto getStoreDto(){
        return CreateStoreDto.builder()
                .name(NAME)
                .address(ADDRESS)
                .businessHours(BUSINESS_HOURS)
                .phone(PHONE)
                .imageUrls(IMAGE_URLS)
                .webpageUrl(WEBPAGE_URL)
                .kakaoMapUrl(KAKAOMAP_URL)
                .score(SCORE)
                .build();
    }
}
