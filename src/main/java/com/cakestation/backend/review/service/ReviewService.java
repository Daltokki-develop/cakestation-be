package com.cakestation.backend.review.service;

import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.ReviewTag;
import com.cakestation.backend.review.dto.request.CreateReviewDto;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(Long storeId, CreateReviewDto createReviewDto){
        // TODO 사용자 ID 받아오도록 추후 수정 필요
        Long userId = 1L;

        // 엔티티 조회
        User user = userRepository.findById(userId).get();
        Store store = storeRepository.findById(storeId).get();

        // 리뷰 생성
        Review review = Review.createReview(user, store, createReviewDto);
        reviewRepository.save(review);

        return review.getId();
    }

    public Review findReviewById(Long reviewId){
        return reviewRepository.findById(reviewId).get();
    }
}
