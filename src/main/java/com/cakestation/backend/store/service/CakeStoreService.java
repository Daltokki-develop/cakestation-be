package com.cakestation.backend.store.service;

import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.store.domain.CakeStore;
import com.cakestation.backend.store.repository.User_StoreRepository;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.store.service.dto.CakeStoreDto;
import com.cakestation.backend.store.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CakeStoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    private final User_StoreRepository user_storeRepository;

    @Transactional
    public Long saveStore(CreateCakeStoreDto createStoreDto) {
        CakeStore store = CakeStore.createCakeStore(createStoreDto);
        return storeRepository.save(store).getId();
    }

    public CakeStoreDto findStoreById(Long storeId) {
        CakeStore store = storeRepository.findById(storeId).orElseThrow(() -> new IdNotFoundException("가게를 찾을 수 없습니다."));
        return CakeStoreDto.from(store);
    }

    public List<CakeStoreDto> findAllStore() {
        return storeRepository.findAll().stream().map(CakeStoreDto::from).collect(Collectors.toList());
    }

    public List<CakeStoreDto> searchStoresByKeyword(String keyword, Pageable pageable) {
        List<CakeStore> stores = storeRepository.findAllByNameContains(keyword, pageable);
        return stores.stream().map(CakeStoreDto::from).collect(Collectors.toList());
    }

    public CakeStoreDto likeStore(Long storeId, String userEmail) {

        // 가게 찾기
        CakeStore likeStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new IdNotFoundException("존재하지 않는 가게입니다."));;

        // 유저 찾기
       User targetUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new IdNotFoundException("유저 정보가 잘못되었습니다."));

        // user_store 조인테이블에 값 입력
//        user_storeRepository.
//
        return CakeStoreDto.from(likeStore);
        // TODO()
    }
}
