package com.cakestation.backend.cakestore.service;

import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.LikeStoreRepository;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CakeStoreService {
    private final CakeStoreRepository cakeStoreRepository;
    private final UserRepository userRepository;
    private final LikeStoreRepository likeStoreRepository;

    @Transactional
    public Long saveStore(CreateCakeStoreDto createStoreDto) {
        CakeStore store = CakeStore.createCakeStore(createStoreDto);
        return cakeStoreRepository.save(store).getId();
    }

    public CakeStoreDto findStoreById(Long storeId) {
        CakeStore store = cakeStoreRepository.findById(storeId)
                .orElseThrow(() -> new IdNotFoundException("가게를 찾을 수 없습니다."));
        return CakeStoreDto.from(store);
    }

    public List<CakeStoreDto> findAllStore() {
        return cakeStoreRepository.findAll()
                .stream()
                .map(CakeStoreDto::from)
                .collect(Collectors.toList());
    }

    public List<CakeStoreDto> searchStoresByKeyword(String storeName) {
        List<CakeStore> stores = cakeStoreRepository.findAllByNameContains(storeName);
        return stores.stream()
                .map(CakeStoreDto::from)
                .collect(Collectors.toList());
    }

    public List<CakeStoreDto> searchStoresByStation(String stationName) {
        List<CakeStore> stores = cakeStoreRepository.findAllByNearByStationContains(stationName);
        return stores.stream()
                .map(CakeStoreDto::from)
                .collect(Collectors.toList());
    }


    @Transactional
    public Long likeStore(Long storeId, String userEmail) {

        CakeStore cakeStore = cakeStoreRepository.findById(storeId)
                .orElseThrow(() -> new IdNotFoundException("존재하지 않는 가게입니다."));
        User targetUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new IdNotFoundException("유저 정보가 잘못되었습니다."));

        likeStoreRepository.findByUserAndCakeStore(targetUser, cakeStore)
                .ifPresent(likeStore -> {
                    throw new IllegalArgumentException("이미 좋아요가 등록된 가게입니다.");
                });

        return likeStoreRepository.save(LikeStore.createLikeStore(targetUser, cakeStore)).getId();
    }

    public List<CakeStoreDto> findAllLikeStore(String userEmail) {

        User targetUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new IdNotFoundException("유저 정보가 잘못되었습니다."));

        List<CakeStore> cakeStores = likeStoreRepository.findLikeStoresByUser(targetUser)
                .stream()
                .map(LikeStore::getCakeStore)
                .collect(Collectors.toList());

        List<Long> cakeStoreIds = cakeStores.stream().map(CakeStore::getId).collect(Collectors.toList());

        return cakeStoreRepository.findAllById(cakeStoreIds)
                .stream()
                .map(CakeStoreDto::from)
                .collect(Collectors.toList());
    }
}
