package com.cakestation.backend.review.service;

import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.ReviewImageDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReviewService {
    Long saveReview(CreateReviewDto createReviewDto, String currentEmail);
    List<ReviewDto> findReviewsByWriter(Long writerId, Pageable pageable);
    List<ReviewDto> findReviewsByStore(Long storeId, Pageable pageable);
    void deleteReview(Long reviewId, String currentEmail);
    List<ReviewImageDto> findReviewImagesByStore(Long storeId, Pageable pageable);
}
