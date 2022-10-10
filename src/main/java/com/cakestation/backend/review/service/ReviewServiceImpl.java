package com.cakestation.backend.review.service;

import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.ReviewImageDto;
import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;


    // 리뷰 등록
    @Override
    @Transactional
    public Long saveReview(CreateReviewDto createReviewDto, String currentEmail) {

        // 검증
        User user = userRepository.findUserByEmail(currentEmail).orElseThrow(
                () -> new IdNotFoundException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(createReviewDto.getStoreId())
                .orElseThrow(() -> new IdNotFoundException("가게 정보를 찾을 수 없습니다."));

        // 리뷰 생성
        List<String> imageUrls = imageUploadService.uploadFiles(createReviewDto.getReviewImages());
        createReviewDto.setImageUrls(imageUrls);
        Review review = reviewRepository.save(Review.createReview(user, store, createReviewDto));

        return review.getId();
    }

    @Override
    public ReviewDto findReviewById(Long reviewId) {
        Review review =
                reviewRepository.findById(reviewId).orElseThrow(() -> new IdNotFoundException("리뷰 정보를 찾을 수 없습니다"));

        return ReviewDto.from(review);

    }

    // 리뷰 조회 by writer
    @Override
    public List<ReviewDto> findReviewsByWriter(Long writerId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByWriter(writerId, pageable);
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }

    // 리뷰 조회 by store
    @Override
    public List<ReviewDto> findReviewsByStore(Long storeId, Pageable pageable) {

        List<Review> reviews = reviewRepository.findAllByStore(storeId, pageable);
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }

    // 리뷰 별점 평균 조회 by store
    @Override
    public Double findReviewAvgByStore(Long storeId) {
        return reviewRepository.findAverageByStore(storeId).orElseThrow(
                () -> new IdNotFoundException("가게 정보를 찾을 수 없습니다.")
        );
    }


    // 리뷰 삭제
    @Override
    @Transactional
    public void deleteReview(Long reviewId, String currentEmail) {
        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IdNotFoundException("리뷰가 존재하지 않습니다."));

        // 권한 조회
        if (review.getWriter().getEmail().equals(currentEmail)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new IdNotFoundException("권한이 없습니다.");
        }
    }

    // 가게별 리뷰 이미지 전체 조회
    @Override
    public List<ReviewImageDto> findReviewImagesByStore(Long storeId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByStore(storeId, pageable);

        List<List<ReviewImageDto>> allReviewImageDto = getAllReviewImageDto(reviews);

        List<ReviewImageDto> reviewImageDtoList = new ArrayList<>();
        for (List<ReviewImageDto> imageDtoList : allReviewImageDto) {
            reviewImageDtoList.addAll(imageDtoList);
        }
        return reviewImageDtoList;
    }

    private List<List<ReviewImageDto>> getAllReviewImageDto(List<Review> reviews) {
        return reviews.stream().map(
                review -> review.getImageUrls().stream().map(
                        url -> new ReviewImageDto(review.getId(), url)
                ).collect(Collectors.toList())).collect(Collectors.toList());
    }
}
