package com.cakestation.backend.cakestore.controller.dto.response;

public interface LikeStoreResponseInterface {
    Integer getStoreId();
    String getAddress();
    String getBusinessHours();
    String getKakaoMapUrl();
    String getPhoneNumber();
    String getWebPageUrl();
    String getStoreName();

    //TODO nearByStation , thumbnail 데이터 출처 확인하기
}