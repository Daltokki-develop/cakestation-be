package com.cakestation.backend.store.service;

import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long saveStore(CreateStoreDto createStoreDto) {
        User user = userRepository.findById(createStoreDto.getUserId()).get();
        Store store = Store.createStore(user,createStoreDto);
        return storeRepository.save(store).getId();
    }
}
