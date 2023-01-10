package com.cakestation.backend.cakestore.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.common.annotations.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("CakeStoreQueryService 는 ")
@ServiceTest
class CakeStoreQueryServiceTest {
    @Autowired
    CakeStoreRepository cakeStoreRepository;

    @Autowired
    CakeStoreQueryService cakeStoreQueryService;

    @BeforeEach
    void beforeEach() {
        cakeStoreRepository.deleteAll();
    }

    @DisplayName("가게 아이디로 케이크가게를 조회할 수 있다.")
    @Test
    void getStoreById() {
        cakeStoreRepository.save(new CakeStore(STORE_ID_1, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION, List.of(), 0, 0));
        CakeStoreDto store = cakeStoreQueryService.findStoreById(STORE_ID_1);

        assertThat(store.getStoreId()).isEqualTo(STORE_ID_1);
    }

    @DisplayName("케이크라는 검색어를 입력했을 때 케이크라는 이름이 들어간 가게들을 조회할 수 있다.")
    @Test
    void getStoresByStoreName() {
        cakeStoreRepository.save(new CakeStore(STORE_ID_1, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION, List.of(), 0, 0));
        cakeStoreRepository.save(new CakeStore(STORE_ID_2, NAME_2, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION, List.of(), 0, 0));
        cakeStoreRepository.save(new CakeStore(STORE_ID_3, NAME_3, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION, List.of(), 0, 0));

        List<CakeStoreDto> cakeStores = cakeStoreQueryService.searchStoresByKeyword("케이크", PageRequest.of(0, 10));
        List<String> cakeStoreNames = cakeStores.stream().map(CakeStoreDto::getName).collect(Collectors.toList());

        cakeStoreNames.forEach(name -> {
            assertThat(name).contains("케이크");
        });
        assertThat(cakeStoreNames.size()).isEqualTo(3);
    }

    @DisplayName("홍대입구를 검색했을 때 홍대입구 근처에 위치한 케이크 가게들을 조회할 수 있다.")
    @Test
    void getStoresByNearByStation() {
        cakeStoreRepository.save(new CakeStore(STORE_ID_1, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, "홍대입구", List.of(), 0, 0));
        cakeStoreRepository.save(new CakeStore(STORE_ID_2, NAME_2, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, "룰루", List.of(), 0, 0));
        cakeStoreRepository.save(new CakeStore(STORE_ID_3, NAME_3, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, "랄라", List.of(), 0, 0));

        List<CakeStoreDto> cakeStores = cakeStoreQueryService.searchStoresByStation("홍대입구", PageRequest.of(0, 3));
        List<String> stationNames = cakeStores.stream().map(CakeStoreDto::getNearByStation).collect(Collectors.toList());

        stationNames.forEach(name -> {
            assertThat(name).contains("홍대입구");
        });
        assertThat(stationNames.size()).isEqualTo(1);
    }
}