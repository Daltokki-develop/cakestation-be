package com.cakestation.backend.review.service;

import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final UtilService utilService;

    // 리뷰 등록
    @Transactional
    public Long saveReview(CreateReviewDto createReviewDto){
        // TODO ("현재 사용자 이메일 가져오도록 변경 필요")
//        String email = utilService.getCurrentUserEmail(request).orElseThrow(RuntimeException::new);
//        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IdNotFoundException("회원 정보를 찾을 수 없습니다."));

        // 엔티티 조회
        User user = userRepository.findById(1L).orElseThrow(() -> new IdNotFoundException("회원 정보를 찾을 수 없습니다."));;
        Store store = storeRepository.findById(createReviewDto.getStoreId())
                .orElseThrow(()-> new IdNotFoundException("가게 정보를 찾을 수 없습니다."));

        // 리뷰 생성
        List<String> imageUrls = imageUploadService.uploadFiles(createReviewDto.getReviewImages());
        createReviewDto.setImageUrls(imageUrls);
        Review review = reviewRepository.save(Review.createReview(user, store, createReviewDto));

        return review.getId();
    }

    // 리뷰 조회 by writer
    public List<ReviewDto> findReviewsByWriter(Long writerId) {
        // TODO: 실제 사용자로 변경 필요
        List<Review> reviews = reviewRepository.findAllByWriter(writerId);
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }

    // 리뷰 조회 by store
    public List<ReviewDto> findReviewsByStore(Long storeId){
        List<Review> reviews = reviewRepository.findAllByStore(storeId);
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }

}
