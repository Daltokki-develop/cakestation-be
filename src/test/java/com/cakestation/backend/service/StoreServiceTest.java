package com.cakestation.backend.service;

import com.cakestation.backend.domain.Store;
import com.cakestation.backend.repository.StoreRepository;
import com.cakestation.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static com.cakestation.backend.fixture.StoreFixture.getStore;
import static com.cakestation.backend.fixture.UserFixture.getUser;
import static org.mockito.ArgumentMatchers.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cakestation.backend.fixture.StoreFixture.getStoreDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private UserRepository userRepository;

    private StoreService storeService;

    @BeforeEach
    void setup() {
        storeService = new StoreService(storeRepository,userRepository);
    }

    @Test
    public void 가게등록(){
        //given
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(getUser()));
        Store store = getStore();
        Mockito.when(storeRepository.save(any(Store.class))).thenReturn(store);

        //when
        Long storeId = storeService.saveStore(getStoreDto());

        //then
        assertEquals(store.getId(),storeId);

        //verify
        verify(storeRepository, times(1)).save(any(Store.class));

        /**
         *     verify(userRepository, times(1)).save(any(User.class));
         *     verify(passwordEncoder, times(1)).encode(any(String.class));
         */
    }
}