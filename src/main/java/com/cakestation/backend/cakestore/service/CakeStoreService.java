package com.cakestation.backend.cakestore.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.cakestore.exception.InvalidStoreException;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.cakestore.repository.LikeStoreRepository;
import com.cakestation.backend.cakestore.service.dto.CreateCakeStoreDto;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CakeStoreService {
    private final CakeStoreRepository cakeStoreRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LikeStoreRepository likeStoreRepository;

    @Transactional
    public Long saveStore(CreateCakeStoreDto createStoreDto) {
        CakeStore store = CakeStore.createCakeStore(createStoreDto);

        return cakeStoreRepository.save(store).getId();
    }

    @Transactional
    public void likeStore(Long storeId, String userEmail) {
        CakeStore cakeStore = getCakeStore(storeId);
        User targetUser = getUser(userEmail);

        likeStoreRepository.findByUserAndCakeStore(targetUser, cakeStore)
                .ifPresentOrElse(likeStoreRepository::delete,
                        () -> likeStoreRepository.save(LikeStore.createLikeStore(targetUser, cakeStore)));
    }

    @Transactional
    public void deleteStore(Long storeId) {
        CakeStore cakeStore = getCakeStore(storeId);

        List<Long> reviewIds = cakeStore.getReviews()
                .stream()
                .map(Review::getId)
                .collect(Collectors.toList());

        reviewRepository.deleteReviewImagesByReviewIds(reviewIds);
        reviewRepository.deleteReviewTagsByReviewIds(reviewIds);
        reviewRepository.deleteReviewByIds(reviewIds);

        cakeStoreRepository.deleteById(cakeStore.getId());
    }

    private CakeStore getCakeStore(Long storeId) {

        return cakeStoreRepository.findById(storeId)
                .orElseThrow(() -> new InvalidStoreException(ErrorType.NOT_FOUND_STORE));
    }

    private User getUser(String userEmail) {

        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
    }
}
