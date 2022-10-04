package com.cakestation.backend.review.controller.dto.response;

import com.cakestation.backend.review.service.dto.ReviewImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImageResponse {
    private Long reviewId;
    private String imageUrl;

    public static ReviewImageResponse from(ReviewImageDto reviewImageDto) {
        return new ReviewImageResponse(
                reviewImageDto.getReviewId(),
                reviewImageDto.getImageUrl()
        );
    }
}
