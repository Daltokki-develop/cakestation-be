package com.cakestation.backend.cakestore.service;

import java.util.Optional;

import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CakeStoreServiceUnitTest {

    @Mock
    CakeStoreRepository cakeStoreRepository;

    @InjectMocks
    CakeStoreService cakeStoreService;

    @Test
    void 가게_등록() {
    }

    @Test
    public void 가게_조회() {
        // given
        doReturn(Optional.of(getCakeStoreEntity())).when(cakeStoreRepository).findById(any());

        // when
        CakeStoreDto store = cakeStoreService.findStoreById(STORE_ID);

        // then
        assertEquals(store.getStoreId(),STORE_ID);
    }
}