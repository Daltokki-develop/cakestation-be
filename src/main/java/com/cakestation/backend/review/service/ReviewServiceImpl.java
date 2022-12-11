package com.cakestation.backend.review.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.exception.InvalidStoreException;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.ReviewImage;
import com.cakestation.backend.review.domain.ReviewTag;
import com.cakestation.backend.review.domain.Tag;
import com.cakestation.backend.review.exception.InvalidReviewException;
import com.cakestation.backend.review.repository.ReviewImageRepository;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.repository.ReviewTagRepository;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.ReviewImageDto;
import com.cakestation.backend.review.service.dto.UpdateReviewDto;
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
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewTagRepository reviewTagRepository;


    @Override
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

    @Override
    @Transactional
    public ReviewDto updateReview(UpdateReviewDto updateReviewDto, Long reviewId) {
        reviewImageRepository.deleteReviewImageByIds(reviewImageRepository.findAllIdByReviewId(reviewId));
        reviewTagRepository.deleteReviewTagByIds(reviewTagRepository.findAllIdByReviewId(reviewId));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new InvalidReviewException(ErrorType.NOT_FOUND_REVIEW));

        List<String> imageUrls = imageUploadService.uploadFiles(updateReviewDto.getReviewImages());
        updateReviewDto.setImageUrls(imageUrls);

        review.update(updateReviewDto);

        return ReviewDto.from(review, getTags(review), imageUrls);
    }

    @Override
    public ReviewDto findReviewById(Long reviewId) {
        Review review =
                reviewRepository.findById(reviewId).orElseThrow(
                        () -> new InvalidReviewException(ErrorType.NOT_FOUND_REVIEW));

        List<Tag> tags = getTags(review);
        return ReviewDto.from(review, tags, getImageUrls(review));
    }

    @Override
    public List<ReviewDto> findReviewsByWriter(Long writerId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByWriterId(writerId, pageable);
        return reviews.stream()
                .map(review -> ReviewDto.from(review, getTags(review), getImageUrls(review)))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> findReviewsByStore(Long cakeStoreId, Pageable pageable) {

        List<Review> reviews = reviewRepository.findAllByCakeStoreId(cakeStoreId, pageable);
        return reviews.stream()
                .map(review -> ReviewDto.from(review, getTags(review), getImageUrls(review)))
                .collect(Collectors.toList());
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

        if (!review.getWriter().getEmail().equals(currentEmail)) {
            throw new InvalidUserException(ErrorType.FORBIDDEN);
        }

        reviewImageRepository.deleteReviewImageByIds(reviewImageRepository.findAllIdByReviewId(reviewId));
        reviewTagRepository.deleteReviewTagByIds(reviewTagRepository.findAllIdByReviewId(reviewId));
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewImageDto> findReviewImagesByStore(Long storeId, Pageable pageable) {
        List<Review> reviews = reviewRepository.findAllByCakeStoreId(storeId, pageable);

        return collectReviewImageDto(reviews);
    }

    @Override
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
