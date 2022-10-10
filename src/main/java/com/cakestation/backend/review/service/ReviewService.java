package com.cakestation.backend.review.service;

import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.ReviewImageDto;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Long saveReview(CreateReviewDto createReviewDto, String currentEmail);
    ReviewDto findReviewById(Long reviewId);
    List<ReviewDto> findReviewsByWriter(Long writerId, Pageable pageable);
    List<ReviewDto> findReviewsByStore(Long storeId, Pageable pageable);
    Double findReviewAvgByStore(Long storeId);
    void deleteReview(Long reviewId, String currentEmail);
    List<ReviewImageDto> findReviewImagesByStore(Long storeId, Pageable pageable);
}
