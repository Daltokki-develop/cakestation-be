package com.cakestation.backend.cakestore.fixture;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;

public class StoreFixture {

    public static Long STORE_ID_1 = 1L;
    public static Long STORE_ID_2 = 2L;
    public static Long STORE_ID_3 = 3L;

    public static String NAME_1 = "베니 케이크";
    public static String NAME_2 = "얌얌 케이크";
    public static String NAME_3 = "송이 케이크";

    public static String ADDRESS = "주소";

    public static String BUSINESS_HOURS = "월~금";

    public static String PHONE = "010-1111-2222";

    public static String THUMNAIL = "aa";

    public static String WEBPAGE_URL = "sflsdkfjlsdf";

    public static String KAKAOMAP_URL = "sdflsdfnlsdkf";

    public static Double SCORE = 5.0;

    public static Integer NUM_OF_PHOTO = 0;

    public static Integer NUM_OF_REVIEWS = 0;

    public static String NEARBY_STATION
            = "[['홍대입구역', ['2호선', '경의중앙선', '공항철도'], '3번 출구', '도보 12분'], ['가좌역', ['경의중앙선'], '1번 출구', '도보 16분']]";


    public static CakeStore getCakeStoreEntity() {
        return CakeStore.builder()
                .name(NAME_1)
                .address(ADDRESS)
                .businessHours(BUSINESS_HOURS)
                .phoneNumber(PHONE)
                .thumbnail(THUMNAIL)
                .webpageUrl(WEBPAGE_URL)
                .mapUrl(KAKAOMAP_URL)
                .nearByStation(NEARBY_STATION)
                .build();
    }

    public static CreateCakeStoreDto getCreateCakeStoreDto() {
        return CreateCakeStoreDto.builder()
                .name(NAME_1)
                .address(ADDRESS)
                .businessHours(BUSINESS_HOURS)
                .phoneNumber(PHONE)
                .thumbnail(THUMNAIL)
                .webpageUrl(WEBPAGE_URL)
                .mapUrl(KAKAOMAP_URL)
                .nearByStation(NEARBY_STATION)
                .build();
    }
}
