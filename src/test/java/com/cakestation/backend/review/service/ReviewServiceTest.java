package com.cakestation.backend.review.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.common.annotations.ServiceTest;
import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.user.domain.Role;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static com.cakestation.backend.cakestore.fixture.StoreFixture.NEARBY_STATION;
import static com.cakestation.backend.review.fixture.ReviewFixture.*;
import static com.cakestation.backend.review.fixture.ReviewFixture.SCORE;
import static com.cakestation.backend.user.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    }

    @DisplayName("리뷰를 등록할 수 있다.")
    @Test
    void saveReview() {
        userRepository.save(new User(USER_ID, USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        cakeStoreRepository.save(new CakeStore(STORE_ID_1, NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION, List.of(), 0, 0));

        CreateReviewDto createReviewDto = new CreateReviewDto(STORE_ID_1, ENCODING_REVIEW_IMAGES, IMAGE_URLS, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, TAGS, CONTENT);

        Long reviewId = reviewService.saveReview(createReviewDto, EMAIL);

        assertThat(reviewId).isNotNull();
    }

}