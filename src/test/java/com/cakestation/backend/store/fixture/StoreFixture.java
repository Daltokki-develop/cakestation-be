package com.cakestation.backend.store.fixture;

import com.cakestation.backend.store.domain.CakeStore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

public class StoreFixture {

    public static Long STORE_ID = 1L;

    public static String NAME = "베니 케이크";

    public static String ADDRESS = "주소";

    public static String BUSINESS_HOURS = "월~금";

    public static String PHONE = "010-1111-2222";

    public static String THUMNAIL = "aa";

    public static String WEBPAGE_URL = "sflsdkfjlsdf";

    public static String KAKAOMAP_URL = "sdflsdfnlsdkf";

    public static Double SCORE = 5.0;

    public static Integer NUM_OF_PHOTO = 0;

    public static Integer NUM_OF_REVIEWS = 0;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public static Date UPLOAD_DATE = new Date();

    public static CakeStore storeEntity(){
        return CakeStore.builder()
                .id(STORE_ID)
                .name(NAME)
                .address(ADDRESS)
                .businessHours(BUSINESS_HOURS)
                .phoneNumber(PHONE)
                .thumbnail(THUMNAIL)
                .webpageUrl(WEBPAGE_URL)
                .mapUrl(KAKAOMAP_URL)
                .build();
    }

//    public static CreateStoreDto getStoreDto(){
//        return CreateStoreDto.builder()
//                .name(NAME)
//                .address(ADDRESS)
//                .businessHours(BUSINESS_HOURS)
//                .phoneNumber(PHONE)
//                .thumbnail(THUMNAIL)
//                .webpageUrl(WEBPAGE_URL)
//                .mapUrl(KAKAOMAP_URL)
//                .build();
//    }
}
