package com.cakestation.backend.mypage.service;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.repository.CakeStoreRepository;
import com.cakestation.backend.common.annotations.ServiceTest;
import com.cakestation.backend.mypage.service.dto.MyPageDto;
import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.domain.Role;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.getCakeStoreEntity;
import static com.cakestation.backend.review.fixture.ReviewFixture.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("MyPageService 는 ")
@ServiceTest
class MyPageServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CakeStoreRepository cakeStoreRepository;
    @Autowired
    MyPageService myPageService;

    @BeforeEach
    void beforeEach() {
        reviewRepository.deleteAll();
        userRepository.deleteAll();
        cakeStoreRepository.deleteAll();
    }

    @DisplayName("리뷰개수가 1개일 때 마이페이지 정보의 리뷰 개수는 1개이다.")
    @Test
    void getMyPageInfo() {
        User user = new User(null, "유저넴", "닉넴", "mm@mail.com", 1, Role.ROLE_USER);
        CakeStore cakeStore = getCakeStoreEntity();
        Review review = new Review(null, user, cakeStore, List.of(), CAKE_NUMBER, SHEET_TYPE, REQUEST_OPTION, DesignSatisfaction.GOOD, SCORE, List.of(), CONTENT);

        userRepository.save(user);
        cakeStoreRepository.save(cakeStore);
        reviewRepository.save(review);

        MyPageDto myPageInfo = myPageService.getMyPageInfo("mm@mail.com");

        assertAll(
                () -> assertThat(myPageInfo.getReviewCount(), is(equalTo(1))),
                () -> assertThat(myPageInfo.getReviewImageCount(), is(equalTo(0)))
        );
    }
}