package com.cakestation.backend.mypage.service;

import com.cakestation.backend.mypage.service.dto.MyPageDto;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cakestation.backend.review.fixture.ReviewFixture.IMAGE_URLS;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.cakestation.backend.review.fixture.ReviewFixture.getReviewEntity;
import static com.cakestation.backend.user.fixture.UserFixture.EMAIL;
import static com.cakestation.backend.user.fixture.UserFixture.getUserEntity;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    ReviewRepository reviewRepository;
    @InjectMocks
    MyPageService myPageService;

    @Test
    void 마이페이지_정보_조회(){
        // given
        doReturn(Optional.of(getUserEntity())).when(userRepository).findUserByEmail(any());
        doReturn(Optional.of(getReviewEntity())).when(reviewRepository).findAllByWriter(any());
        // when
        MyPageDto myPageInfo = myPageService.getMyPageInfo(EMAIL);
        // then

        assertThat(myPageInfo.getRandomNumber(), is(greaterThanOrEqualTo(0)));
        assertThat(myPageInfo.getRandomNumber(), is(lessThanOrEqualTo(4)));
        assertThat(myPageInfo.getReviewCount(),is(equalTo(1)));
        assertThat(myPageInfo.getReviewCount(),is(equalTo(1)));
        assertThat(myPageInfo.getReviewImageCount(),is(equalTo(IMAGE_URLS.size())));

    }
}