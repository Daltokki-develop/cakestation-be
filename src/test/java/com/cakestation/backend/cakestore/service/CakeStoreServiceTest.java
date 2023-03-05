package com.cakestation.backend.cakestore.service;

import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.common.annotations.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.getCreateCakeStoreDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("CakeStoreService 는 ")
@ServiceTest
class CakeStoreServiceTest {

    @Autowired
    CakeStoreRepository cakeStoreRepository;

    @Autowired
    CakeStoreService cakeStoreService;

    @BeforeEach
    void beforeEach() {
        cakeStoreRepository.deleteAll();
    }

    @DisplayName("가게를 등록할 수 있다.")
    @Test
    void save_store() {
        Long cakeStoreId = cakeStoreService.saveStore(getCreateCakeStoreDto());

        assertThat(cakeStoreId).isNotNull();
    }
}