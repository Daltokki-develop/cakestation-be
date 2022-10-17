package com.cakestation.backend.store.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.domain.User_Store;
import com.cakestation.backend.store.repository.User_StoreRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.store.dto.request.CreateStoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    private final User_StoreRepository user_storeRepository;

    @Transactional
    public Long saveStore(CreateStoreDto createStoreDto) {
        //TODO user id 가져오도록 수정
        Long userId = 1L;
        User user = userRepository.findById(userId).orElseThrow(()-> new IdNotFoundException("사용자를 찾을 수 없습니다."));
        Store store = Store.createStore(user, createStoreDto);
        return storeRepository.save(store).getId();
    }

    public StoreDto findStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(()-> new IdNotFoundException("가게를 찾을 수 없습니다."));
        return StoreDto.from(store);
    }

    public List<StoreDto> findAllStore() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(StoreDto::from).collect(Collectors.toList());
    }

    public List<StoreDto> searchStoresByKeyword(String keyword, Pageable pageable) {
        List<Store> stores = storeRepository.findAllByNameContains(keyword, pageable);
        return stores.stream().map(StoreDto::from).collect(Collectors.toList());
    }

//    public Store likeStore(Long storeId, String userEmail){
//
//        // 가게 찾기
//        Optional<Store> likeStore = storeRepository.findById(storeId);
//        likeStore.orElseThrow(() -> new IdNotFoundException("존재하지 않는 가게입니다."));
//
//        // 유저 찾기
//        Optional<User> targetUser = userRepository.findUserByEmail(userEmail);
//        targetUser.orElseThrow(() -> new IdNotFoundException("유저 정보가 잘못되었습니다."));
//
//        // user_store 조인테이블에 값 입력
//        user_storeRepository.
//
//        return Store;
//    }
}
