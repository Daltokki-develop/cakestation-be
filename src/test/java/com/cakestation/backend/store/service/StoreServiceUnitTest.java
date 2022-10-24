//package com.cakestation.backend.store.service;
//
//import java.util.Optional;
//
//import com.cakestation.backend.store.domain.Store;
//import com.cakestation.backend.store.repository.StoreRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static com.cakestation.backend.review.fixture.ReviewFixture.reviewEntity;
//import static com.cakestation.backend.store.fixture.StoreFixture.STORE_ID;
//import static com.cakestation.backend.store.fixture.StoreFixture.storeEntity;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class StoreServiceUnitTest {
//
//    @Mock
//    StoreRepository storeRepository;
//
//    @InjectMocks
//    StoreService storeService;
//
//    @Test
//    void 가게_등록() {
//    }
//
//    @Test
//    public void 가게_조회() {
//        // given
//        doReturn(Optional.of(storeEntity())).when(storeRepository).findById(any());
//
//        // when
//        StoreDto store = storeService.findStoreById(STORE_ID);
//
//        // then
//        assertEquals(store.getStoreId(),STORE_ID);
//    }
//}