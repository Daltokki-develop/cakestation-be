package com.cakestation.backend.review.service;

import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.dto.request.CreateReviewDto;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.exception.InvalidStoreIdException;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(Long storeId, CreateReviewDto createReviewDto){
        String email = UtilService.getCurrentUserEmail().orElseThrow(RuntimeException::new);

        // 엔티티 조회
        User user = userRepository.findUserByEmail(email);

        Store store = storeRepository.findById(storeId).orElseThrow(InvalidStoreIdException::new);

        // 리뷰 생성
        Review review = reviewRepository.save(Review.createReview(user, store, createReviewDto));

        return review.getId();
    }

    public Review findReviewById(Long reviewId){
        // TODO 예외 처리 필요
        return reviewRepository.findById(reviewId).get();
    }
}
