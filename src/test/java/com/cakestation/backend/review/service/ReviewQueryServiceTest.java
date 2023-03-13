package com.cakestation.backend.review.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.common.annotations.ServiceTest;
import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static com.cakestation.backend.review.fixture.ReviewFixture.*;
import static com.cakestation.backend.review.fixture.ReviewFixture.SCORE;
import static com.cakestation.backend.user.fixture.UserFixture.*;
import static com.cakestation.backend.user.fixture.UserFixture.ROLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReviewQueryService 는 ")
@ServiceTest
class ReviewQueryServiceTest {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewQueryService reviewQueryService;
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

    @DisplayName("사용자 아이디로 사용자가 작성한 리뷰를 조회할 수 있다.")
    @Test
    void find_reviews_with_user_id() {
        User writer = userRepository.save(new User(USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        CakeStore cakeStore = cakeStoreRepository.save(new CakeStore(NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION));
        CakeStore cakeStore2 = cakeStoreRepository.save(new CakeStore(NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION));

        Review review1 = new Review(writer, cakeStore, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, CONTENT);
        Review review2 = new Review(writer, cakeStore2, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, CONTENT);

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<ReviewDto> reviews = reviewQueryService.findReviewsByWriter(writer.getId(), PageRequest.of(0, 10));

        assertThat(reviews).hasSize(2);
    }

    @DisplayName("가게 아이디로 가게의 리뷰를 조회할 수 있다.")
    @Test
    void find_reviews_with_store_id() {
        User writer = userRepository.save(new User(USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        CakeStore cakeStore = cakeStoreRepository.save(new CakeStore(NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION));

        Review review = reviewRepository.save(new Review(writer, cakeStore, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, CONTENT));

        List<ReviewDto> reviews = reviewQueryService.findReviewsByStore(review.getCakeStore().getId(), PageRequest.of(0, 10));

        assertAll(
                () -> assertThat(reviews).hasSize(1),
                () -> assertThat(reviews.get(0).getStoreId()).isEqualTo(cakeStore.getId())
        );
    }
}