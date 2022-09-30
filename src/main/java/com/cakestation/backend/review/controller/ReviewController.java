package com.cakestation.backend.review.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.review.controller.dto.request.CreateReviewRequest;
import com.cakestation.backend.review.controller.dto.response.ReviewResponse;
import com.cakestation.backend.review.service.ReviewService;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.user.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final UtilService utilService;

    // 리뷰 등록
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/stores/{storeId}/reviews")
    public ResponseEntity<ApiResponse<Long>> uploadReview(@PathVariable Long storeId,
                                                          @ModelAttribute CreateReviewRequest createReviewRequest,
                                                          HttpServletRequest req) {

        String userEmail = utilService.getCurrentUserEmail(req);
        Long reviewId = reviewService.saveReview(createReviewRequest.toServiceDto(storeId, createReviewRequest), userEmail);
        return ResponseEntity.ok().body(
                new ApiResponse<Long>(HttpStatus.CREATED.value(), "리뷰 등록 성공", reviewId));
    }

    // 리뷰 조회 by writer id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{writerId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByWriter(
            @PathVariable Long writerId,
            @PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        List<ReviewResponse> reviews = reviewService.findReviewsByWriter(writerId, pageable)
                .stream().map(ReviewResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "리뷰 조회 성공", reviews)
        );
    }

    // 리뷰 조회 by store id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByStore(
            @PathVariable Long storeId,
            @PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ReviewResponse> reviews = reviewService.findReviewsByStore(storeId, pageable)
                .stream().map(ReviewResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "리뷰 조회 성공", reviews)
        );
    }

    // 리뷰 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, HttpServletRequest req) {
        String email = utilService.getCurrentUserEmail(req);
        reviewService.deleteReview(reviewId, email);
        return ResponseEntity.noContent().build();
    }
}
