package com.cakestation.backend.cakestore.service;

import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.exception.InvalidStoreException;
import com.cakestation.backend.cakestore.repository.LikeStoreRepository;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.cakestore.service.dto.CakeStoreDto;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
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
                .orElseThrow(() -> new InvalidStoreException(ErrorType.NOT_FOUND_STORE));
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
    public void likeStore(Long storeId, String userEmail) {
        CakeStore cakeStore = cakeStoreRepository.findById(storeId)
                .orElseThrow(() -> new InvalidStoreException(ErrorType.NOT_FOUND_STORE));
        User targetUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));

        likeStoreRepository.findByUserAndCakeStore(targetUser, cakeStore)
                .ifPresentOrElse(likeStoreRepository::delete,
                        () -> likeStoreRepository.save(LikeStore.createLikeStore(targetUser, cakeStore)));
    }

    public List<CakeStoreDto> findAllLikeStore(String userEmail) {
        User targetUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));

        List<CakeStore> cakeStores = likeStoreRepository.findLikeStoresByUser(targetUser)
                .stream()
                .map(LikeStore::getCakeStore)
                .collect(Collectors.toList());

        List<Long> cakeStoreIds = cakeStores
                .stream()
                .map(CakeStore::getId)
                .collect(Collectors.toList());

        return cakeStoreRepository.findAllById(cakeStoreIds)
                .stream()
                .map(CakeStoreDto::from)
                .collect(Collectors.toList());
    }
}
