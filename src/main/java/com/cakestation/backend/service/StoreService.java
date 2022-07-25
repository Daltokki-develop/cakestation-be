package com.cakestation.backend.service;

import com.cakestation.backend.domain.Store;
import com.cakestation.backend.domain.User;
import com.cakestation.backend.repository.StoreRepository;
import com.cakestation.backend.repository.UserRepository;
import com.cakestation.backend.service.dto.request.CreateStoreDto;
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
        Store store = CreateStoreDto.toEntity(user,createStoreDto);
        return storeRepository.save(store).getId();
    }
}
