package com.cakestation.backend.store.service;

import java.util.Optional;

import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.store.service.dto.CakeStoreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.cakestation.backend.store.fixture.StoreFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceUnitTest {

    @Mock
    StoreRepository storeRepository;

    @InjectMocks
    CakeStoreService cakeStoreService;

    @Test
    void 가게_등록() {
    }

    @Test
    public void 가게_조회() {
        // given
        doReturn(Optional.of(getCakeStoreEntity())).when(storeRepository).findById(any());

        // when
        CakeStoreDto store = cakeStoreService.findStoreById(STORE_ID);

        // then
        assertEquals(store.getStoreId(),STORE_ID);
    }
}