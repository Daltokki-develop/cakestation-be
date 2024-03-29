package com.cakestation.backend.review.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.common.annotations.ServiceTest;
import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.UpdateReviewDto;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static com.cakestation.backend.review.fixture.ReviewFixture.SCORE;
import static com.cakestation.backend.review.fixture.ReviewFixture.*;
import static com.cakestation.backend.user.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ReviewService 는 ")
@ServiceTest
class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CakeStoreRepository cakeStoreRepository;

    @BeforeEach
    void beforeEach() {
        reviewRepository.deleteAll();
        cakeStoreRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("리뷰를 등록할 수 있다.")
    @Test
    void save_review() {
        userRepository.save(new User(USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        CakeStore cakeStore = cakeStoreRepository.save(new CakeStore(NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION));

        CreateReviewDto createReviewDto = new CreateReviewDto(cakeStore.getId(), ENCODING_REVIEW_IMAGES, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, TAGS, CONTENT);
        Long reviewId = reviewService.saveReview(createReviewDto, EMAIL);

        assertThat(reviewId).isNotNull();
    }

    @DisplayName("리뷰를 수정할 수 있다.")
    @Test
    void update_review() {
        User user = userRepository.save(new User(USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        CakeStore cakeStore = cakeStoreRepository.save(new CakeStore(NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION));
        Review review = reviewRepository.save(new Review(user, cakeStore, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, CONTENT));

        UpdateReviewDto updateReviewDto = new UpdateReviewDto(cakeStore.getId(), ENCODING_REVIEW_IMAGES, CAKE_NUMBER, "시트변경", REQUEST_OPTION, DesignSatisfaction.NORMAL, SCORE, TAGS, CONTENT);
        ReviewDto reviewDto = reviewService.updateReview(updateReviewDto, review.getId());

        assertAll(
                () -> assertThat(reviewDto.getSheetType()).isEqualTo("시트변경"),
                () -> assertThat(reviewDto.getDesignSatisfaction()).isEqualTo(DesignSatisfaction.NORMAL)
        );
    }
}