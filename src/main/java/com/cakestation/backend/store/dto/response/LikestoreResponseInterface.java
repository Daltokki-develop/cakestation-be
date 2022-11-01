package com.cakestation.backend.store.dto.response;

public interface LikestoreResponseInterface {

    Integer getStoreId();
    String getAddress();
    String getBusinessHours();
    String getKakaoMapUrl();
    String getPhoneNumber();
    String getwebpageUrl();
    String getStoreName();

    //TODO nearByStation , thumbnail 데이터 출처 확인하기
}
