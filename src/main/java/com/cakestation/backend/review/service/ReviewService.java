package com.cakestation.backend.review.service;

import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.dto.request.CreateReviewDto;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final UtilService utilService;

    @Transactional
    public Long saveReview(Long storeId, CreateReviewDto createReviewDto, List<MultipartFile> reviewImages, HttpServletRequest request){
        String email = utilService.getCurrentUserEmail(request).orElseThrow(RuntimeException::new);

        // 엔티티 조회
        // TODO: 실제 사용자로 변경 필요
        Long userId = 1L;
        User user = userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException("회원 정보를 찾을 수 없습니다."));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new IdNotFoundException("가게 정보를 찾을 수 없습니다."));

        // 리뷰 생성
        List<String> imageUrls = imageUploadService.uploadFiles(reviewImages);
        createReviewDto.setImageUrls(imageUrls);
        Review review = reviewRepository.save(Review.createReview(user, store, createReviewDto));

        return review.getId();
    }

    public Review findReviewsByWriter(Long reviewId) {
        // TODO: 실제 사용자로 변경 필요
        Long writerId = 1L;
        return reviewRepository.findAllByWriter(writerId);
    }
}
