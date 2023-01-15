package com.cakestation.backend.review.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.exception.InvalidStoreException;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.ReviewTag;
import com.cakestation.backend.review.domain.Tag;
import com.cakestation.backend.review.exception.InvalidReviewException;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.UpdateReviewDto;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ImageUploadService imageUploadService;
    private final UserRepository userRepository;
    private final CakeStoreRepository cakeStoreRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(CreateReviewDto createReviewDto, String currentEmail) {

        User user = userRepository.findUserByEmail(currentEmail).orElseThrow(
                () -> new InvalidUserException(ErrorType.NOT_FOUND_USER));

        CakeStore cakeStore = cakeStoreRepository.findCakeStoreForUpdateById(createReviewDto.getStoreId())
                .orElseThrow(() -> new InvalidStoreException(ErrorType.NOT_FOUND_STORE));

        List<String> imageUrls = imageUploadService.uploadFiles(createReviewDto.getReviewImages());
        createReviewDto.setImageUrls(imageUrls);

        Review review = reviewRepository.save(Review.createReview(user, cakeStore, createReviewDto));

        return review.getId();
    }

    @Transactional
    public ReviewDto updateReview(UpdateReviewDto updateReviewDto, Long reviewId) {
        reviewRepository.deleteReviewImagesByReviewIds(List.of(reviewId));
        reviewRepository.deleteReviewTagsByReviewIds(List.of(reviewId));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new InvalidReviewException(ErrorType.NOT_FOUND_REVIEW));

        List<String> imageUrls = imageUploadService.uploadFiles(updateReviewDto.getReviewImages());
        updateReviewDto.setImageUrls(imageUrls);

        review.update(updateReviewDto);

        return ReviewDto.from(review, getTags(review), imageUrls);
    }

    @Transactional
    public void deleteReview(Long reviewId, String currentEmail) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new InvalidReviewException(ErrorType.NOT_FOUND_REVIEW));

        if (!review.getWriter().getEmail().equals(currentEmail)) {
            throw new InvalidUserException(ErrorType.FORBIDDEN);
        }

        reviewRepository.deleteReviewImagesByReviewIds(List.of(reviewId));
        reviewRepository.deleteReviewTagsByReviewIds(List.of(reviewId));
        reviewRepository.deleteById(reviewId);
    }

    private List<Tag> getTags(Review review) {
        return review.getReviewTags()
                .stream()
                .map(ReviewTag::getTag)
                .collect(Collectors.toList());
    }
}
