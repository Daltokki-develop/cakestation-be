package com.cakestation.backend.cakestore.service;

import com.cakestation.backend.cakestore.controller.dto.response.LikeStoreResponseInterface;
import com.cakestation.backend.cakestore.domain.User_Store;
import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.User_StoreRepository;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CakeStoreService {
    private final CakeStoreRepository cakeStoreRepository;
    private final UserRepository userRepository;

    private final User_StoreRepository user_storeRepository;

    @Transactional
    public Long saveStore(CreateCakeStoreDto createStoreDto) {
        CakeStore store = CakeStore.createCakeStore(createStoreDto);
        return cakeStoreRepository.save(store).getId();
    }

    public CakeStoreDto findStoreById(Long storeId) {
        CakeStore store = cakeStoreRepository.findById(storeId).orElseThrow(() -> new IdNotFoundException("가게를 찾을 수 없습니다."));
        return CakeStoreDto.from(store);
    }

    public List<CakeStoreDto> findAllStore() {
        return cakeStoreRepository.findAll().stream().map(CakeStoreDto::from).collect(Collectors.toList());
    }

    public List<CakeStoreDto> searchStoresByKeyword(String keyword, Pageable pageable) {
        List<CakeStore> stores = cakeStoreRepository.findAllByNameContains(keyword, pageable);
        return stores.stream().map(CakeStoreDto::from).collect(Collectors.toList());
    }

    @Transactional
    public List<LikeStoreResponseInterface> likeStore(Long storeId, String userEmail){

        // 가게 찾기
        Optional<CakeStore> likeStore = cakeStoreRepository.findById(storeId);
        likeStore.orElseThrow(() -> new IdNotFoundException("존재하지 않는 가게입니다."));

        // 유저 찾기
        Optional<User> targetUser = userRepository.findUserByEmail(userEmail);
        targetUser.orElseThrow(() -> new IdNotFoundException("유저 정보가 잘못되었습니다."));

        Optional<List<User_Store>> findLiked = Optional.ofNullable(user_storeRepository.findByUserAndCakeStore(targetUser.get() , likeStore.get()));

        if(findLiked.get().size() != 0){
            System.out.println(findLiked.get().get(0));
            user_storeRepository.delete(findLiked.get().get(0));
        }
        else{

            //새로운 좋아요 컬럼 생성
            User_Store newLiked = User_Store.createLikeStore(targetUser.get() , likeStore.get());

            // user_store 조인테이블에 값 입력
            user_storeRepository.save(newLiked);

        }

        List<LikeStoreResponseInterface> result = findAllLikedStore(targetUser.get().getEmail());

        return result;
    }

    public List<LikeStoreResponseInterface> findAllLikedStore(String userEmail) {

        // 유저 찾기
        Optional<User> targetUser = userRepository.findUserByEmail(userEmail);
        targetUser.orElseThrow(() -> new IdNotFoundException("유저 정보가 잘못되었습니다."));

        List<LikeStoreResponseInterface> likedStoreList = user_storeRepository.findAllLikedStore(targetUser.get());

        return likedStoreList;
    }
}
