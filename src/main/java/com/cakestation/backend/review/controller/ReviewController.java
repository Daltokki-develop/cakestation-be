package com.cakestation.backend.review.controller;

import com.cakestation.backend.common.ApiResponse;
import com.cakestation.backend.review.controller.dto.request.CreateReviewRequest;
import com.cakestation.backend.review.controller.dto.request.UpdateReviewRequest;
import com.cakestation.backend.review.controller.dto.response.ReviewImageResponse;
import com.cakestation.backend.review.controller.dto.response.ReviewResponse;
import com.cakestation.backend.review.service.ReviewService;
import com.cakestation.backend.review.service.dto.UpdateReviewDto;
import com.cakestation.backend.user.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<ApiResponse<Long>> uploadReview(@RequestHeader("Authorization") String token,
                                                          @PathVariable Long storeId,
                                                          @RequestBody CreateReviewRequest createReviewRequest) {

        String userEmail = utilService.getCurrentUserEmail(token);
        Long reviewId = reviewService.saveReview(createReviewRequest.toServiceDto(storeId, createReviewRequest), userEmail);
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.CREATED.value(), "리뷰 등록 성공", reviewId));
    }

    // 리뷰 수정
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(@PathVariable Long reviewId,
                                                                    @ModelAttribute UpdateReviewRequest updateReviewRequest) {
        UpdateReviewDto updateReviewDto = updateReviewRequest.toServiceDto(reviewId, updateReviewRequest);
        ReviewResponse reviewResponse = ReviewResponse.from(reviewService.updateReview(updateReviewDto, reviewId));
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "리뷰 수정 성공", reviewResponse));
    }

    // 리뷰 단일 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(@PathVariable Long reviewId) {
        ReviewResponse reviewResponse = ReviewResponse.from(reviewService.findReviewById(reviewId));
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "리뷰 조회 성공", reviewResponse));
    }

    // 리뷰 조회 by writer id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByWriter(
            @PathVariable Long userId,
            @PageableDefault(size = 30, sort = {"createdDateTime", "score"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<ReviewResponse> reviews = reviewService.findReviewsByWriter(userId, pageable)
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
            @PageableDefault(size = 30, sort = {"createdDateTime", "score"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<ReviewResponse> reviews = reviewService.findReviewsByStore(storeId, pageable)
                .stream().map(ReviewResponse::from).collect(Collectors.toList());
        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "리뷰 조회 성공", reviews)
        );
    }

    // 리뷰 별점 평균 조회 by store
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/{storeId}/reviews/avg")
    public ResponseEntity<ApiResponse<Double>> getReviewAverageByStore(@PathVariable Long storeId) {
        Double avg = reviewService.findReviewAvgByStore(storeId);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(HttpStatus.OK.value(), "리뷰 별점 평균 조회 성공", avg));
    }

    // 리뷰 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@RequestHeader("Authorization") String token, @PathVariable Long reviewId) {
        String email = utilService.getCurrentUserEmail(token);
        reviewService.deleteReview(reviewId, email);
        return ResponseEntity.noContent().build();
    }

    // 리뷰 이미지 전체 조회 by store id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stores/{storeId}/reviews/image")
    public ResponseEntity<ApiResponse<List<ReviewImageResponse>>> getReviewImagesByStore(
            @PathVariable Long storeId, Pageable pageable) {
        List<ReviewImageResponse> reviewImages = reviewService.findReviewImagesByStore(storeId, pageable)
                .stream()
                .map(ReviewImageResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "리뷰 이미지 조회 성공", reviewImages));

    }

    // 리뷰 이미지 전체 조회 by writer id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/reviews/image")
    public ResponseEntity<ApiResponse<List<ReviewImageResponse>>> getReviewImagesByUser(
            @PathVariable Long userId, Pageable pageable) {
        List<ReviewImageResponse> reviewImages = reviewService.findReviewImagesByUser(userId, pageable)
                .stream()
                .map(ReviewImageResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(
                new ApiResponse<>(HttpStatus.OK.value(), "리뷰 이미지 조회 성공", reviewImages));

    }

}
