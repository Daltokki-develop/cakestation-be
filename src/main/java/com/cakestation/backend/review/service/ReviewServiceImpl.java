package com.cakestation.backend.review.service;

import com.cakestation.backend.cakestore.exception.InvalidStoreException;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.exception.InvalidReviewException;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.ReviewImageDto;
import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
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
    private final CakeStoreRepository cakeStoreRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public Long saveReview(CreateReviewDto createReviewDto, String currentEmail) {

        User user = userRepository.findUserByEmail(currentEmail).orElseThrow(
                () -> new InvalidUserException(ErrorType.NOT_FOUND_USER));

        CakeStore cakeStore = cakeStoreRepository.findById(createReviewDto.getStoreId())
                .orElseThrow(() -> new InvalidStoreException(ErrorType.NOT_FOUND_STORE));

        List<String> imageUrls = imageUploadService.uploadFiles(createReviewDto.getReviewImages());
        createReviewDto.setImageUrls(imageUrls);

        Review review = reviewRepository.save(
                Review.createReview(user, cakeStore, createReviewDto));

        return review.getId();
    }

    @Override
    public ReviewDto findReviewById(Long reviewId) {
        Review review =
                reviewRepository.findById(reviewId).orElseThrow(
                        () -> new InvalidReviewException(ErrorType.NOT_FOUND_REVIEW));
        return ReviewDto.from(review);
    }

    @Override
    public List<ReviewDto> findReviewsByWriter(Long writerId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByWriterWithPaging(writerId, pageable);
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> findReviewsByStore(Long storeId, Pageable pageable) {

        List<Review> reviews = reviewRepository.findAllByStoreWithPaging(storeId, pageable);
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }

    @Override
    public Double findReviewAvgByStore(Long storeId) {
        return reviewRepository.findAverageByStore(storeId).orElseThrow(
                () -> new InvalidStoreException(ErrorType.NOT_FOUND_STORE)
        );
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, String currentEmail) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new InvalidReviewException(ErrorType.NOT_FOUND_REVIEW));

        if (review.getWriter().getEmail().equals(currentEmail)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new InvalidUserException(ErrorType.FORBIDDEN);
        }
    }

    @Override
    public List<ReviewImageDto> findReviewImagesByStore(Long storeId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByStoreWithPaging(storeId, pageable);

        return collectReviewImageDto(reviews);
    }

    @Override
    public List<ReviewImageDto> findReviewImagesByUser(Long userId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByWriterWithPaging(userId, pageable);

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
                .map(review -> review.getImageUrls()
                        .stream()
                        .map(url -> new ReviewImageDto(review.getId(), url))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
