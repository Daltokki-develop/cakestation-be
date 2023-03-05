package com.cakestation.backend.user.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.cakestore.repository.LikeStoreRepository;
import com.cakestation.backend.common.annotations.ServiceTest;
import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.*;
import static com.cakestation.backend.review.fixture.ReviewFixture.SCORE;
import static com.cakestation.backend.review.fixture.ReviewFixture.*;
import static com.cakestation.backend.user.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("UserService 는")
@ServiceTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    LikeStoreRepository likeStoreRepository;
    @Autowired
    CakeStoreRepository cakeStoreRepository;
    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach() {
        reviewRepository.deleteAll();
        likeStoreRepository.deleteAll();
        cakeStoreRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자의 닉네임을 랜덤으로 부여 한다.")
    void make_nickname() {
        User user = userRepository.save(new User(USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));

        assertThat(user.getNickname()).isNotNull();
    }

    @Test
    @DisplayName("사용자의 닉네임을 랜덤으로 재부여 한다.")
    void remake_nickname() {
        // given
        User user = userRepository.save(new User(USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        String beforeNickname = user.getNickname();

        // when
        String newNickname = userService.updateNickname(user.getEmail());

        // then
        assertThat(newNickname).isNotEqualTo(beforeNickname);
    }

    @Test
    @DisplayName("사용자가 탈퇴하면 리뷰와 좋아요 기록도 함께 제거된다.")
    void delete_all_record_if_user_withdrawal() {
        // given
        User user = userRepository.save(new User(USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        CakeStore cakeStore = cakeStoreRepository.save(new CakeStore(NAME_1, ADDRESS, BUSINESS_HOURS, PHONE, THUMNAIL, WEBPAGE_URL, KAKAOMAP_URL, NEARBY_STATION));

        Long likeStoreId = likeStoreRepository.save(new LikeStore(cakeStore, user)).getId();

        Long reviewId1 = reviewRepository.save(new Review(user, cakeStore, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, CONTENT)).getId();
        Long reviewId2 = reviewRepository.save(new Review(user, cakeStore, CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.NOT_GOOD, SCORE, CONTENT)).getId();

        // when
        userService.deleteUser(user.getEmail());

        // then
        assertThat(reviewRepository.findAllById(List.of(reviewId1, reviewId2))).isEqualTo(Collections.EMPTY_LIST);
        assertThat(likeStoreRepository.findById(likeStoreId)).isEmpty();
    }
}
