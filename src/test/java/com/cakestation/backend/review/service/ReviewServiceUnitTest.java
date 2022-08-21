package com.cakestation.backend.review.service;

import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.dto.response.ReviewResponse;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.cakestation.backend.review.fixture.ReviewFixture.*;
import static com.cakestation.backend.store.fixture.StoreFixture.STORE_ID;
import static com.cakestation.backend.store.fixture.StoreFixture.storeEntity;
import static com.cakestation.backend.user.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ReviewServiceUnitTest {

    @Mock
    UserRepository userRepository;
    @Mock
    StoreRepository storeRepository;
    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    ReviewService reviewService;

    @Mock
    ImageUploadService imageUploadService;

    @Test
    void 리뷰_등록(){
        // given
        doReturn(Optional.of(userEntity())).when(userRepository).findById(any());
        doReturn(Optional.of(storeEntity())).when(storeRepository).findById(any());
        doReturn(reviewEntity()).when(reviewRepository).save(any());
        doReturn(new ArrayList<String>()).when(imageUploadService).uploadFiles(IMAGES);

        // when
        Long reviewId = reviewService.saveReview(getCreateReviewDto());

        // then
        assertEquals(reviewId,REVIEW_ID);
    }

    @Test
    void 리뷰_조회_BY_작성자(){
        // given
        doReturn(Collections.singletonList(reviewEntity())).when(reviewRepository).findAllByWriter(any());

        // when
        List<ReviewResponse> reviewResponseList = reviewService.findReviewsByWriter(USER_ID);

        // then
        assertEquals(USERNAME, reviewResponseList.get(0).getUsername());
    }

    @Test
    void 리뷰_조회_BY_가게(){
        // given
        doReturn(Collections.singletonList(reviewEntity())).when(reviewRepository).findAllByStore(any());

        // when
        List<ReviewResponse> reviewResponseList = reviewService.findReviewsByStore(STORE_ID);

        // then
        assertEquals(USERNAME, reviewResponseList.get(0).getUsername());
    }
}