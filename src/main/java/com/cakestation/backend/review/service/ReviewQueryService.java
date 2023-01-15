package com.cakestation.backend.review.service;

import com.cakestation.backend.cakestore.exception.InvalidStoreException;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.ReviewImage;
import com.cakestation.backend.review.domain.ReviewTag;
import com.cakestation.backend.review.domain.Tag;
import com.cakestation.backend.review.exception.InvalidReviewException;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.ReviewImageDto;
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
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public ReviewDto findReviewById(Long reviewId) {
        Review review =
                reviewRepository.findById(reviewId).orElseThrow(
                        () -> new InvalidReviewException(ErrorType.NOT_FOUND_REVIEW));

        List<Tag> tags = getTags(review);
        return ReviewDto.from(review, tags, getImageUrls(review));
    }

    public List<ReviewDto> findReviewsByWriter(Long writerId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByWriterId(writerId, pageable);
        return reviews.stream()
                .map(review -> ReviewDto.from(review, getTags(review), getImageUrls(review)))
                .collect(Collectors.toList());
    }

    public List<ReviewDto> findReviewsByStore(Long cakeStoreId, Pageable pageable) {

        List<Review> reviews = reviewRepository.findAllByCakeStoreId(cakeStoreId, pageable);
        return reviews.stream()
                .map(review -> ReviewDto.from(review, getTags(review), getImageUrls(review)))
                .collect(Collectors.toList());
    }

    public Double findReviewAvgByStore(Long storeId) {
        return reviewRepository.findAverageByStore(storeId).orElseThrow(
                () -> new InvalidStoreException(ErrorType.NOT_FOUND_STORE)
        );
    }

    public List<ReviewImageDto> findReviewImagesByStore(Long storeId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByCakeStoreId(storeId, pageable);

        return collectReviewImageDto(reviews);
    }

    public List<ReviewImageDto> findReviewImagesByUser(Long userId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByWriterId(userId, pageable);

        return collectReviewImageDto(reviews);
    }

    private List<ReviewImageDto> collectReviewImageDto(List<Review> reviews) {
        List<List<ReviewImageDto>> allReviewImageDto = getAllReviewImageDto(reviews);

        List<ReviewImageDto> reviewImageDtoList = new ArrayList<>();
        for (List<ReviewImageDto> imageDtoList : allReviewImageDto) {
            reviewImageDtoList.addAll(imageDtoList);
        }
        return reviewImageDtoList;
    }

    private List<List<ReviewImageDto>> getAllReviewImageDto(List<Review> reviews) {
        return reviews.stream()
                .map(review -> review.getReviewImages()
                        .stream()
                        .map(reviewImage -> new ReviewImageDto(review.getId(), reviewImage.getUrl()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<String> getImageUrls(Review review) {
        return review.getReviewImages()
                .stream()
                .map(ReviewImage::getUrl)
                .collect(Collectors.toList());
    }

    private List<Tag> getTags(Review review) {
        return review.getReviewTags()
                .stream()
                .map(ReviewTag::getTag)
                .collect(Collectors.toList());
    }
}
