package com.cakestation.backend.review.service;

import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.dto.request.CreateReviewDto;
import com.cakestation.backend.review.fixture.ReviewFixture;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

import static com.cakestation.backend.review.fixture.ReviewFixture.REVIEW_ID;
import static com.cakestation.backend.review.fixture.ReviewFixture.reviewEntity;
import static com.cakestation.backend.store.fixture.StoreFixture.STORE_ID;
import static com.cakestation.backend.store.fixture.StoreFixture.storeEntity;
import static com.cakestation.backend.user.fixture.UserFixture.userEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

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
        CreateReviewDto reviewDto = ReviewFixture.reviewDto();

        doReturn(Optional.of(userEntity())).when(userRepository).findById(any());
        doReturn(Optional.of(storeEntity())).when(storeRepository).findById(any());
        doReturn(reviewEntity()).when(reviewRepository).save(any());
        doReturn(new ArrayList<String>()).when(imageUploadService).uploadFiles(new ArrayList<>());

        // when
        Long reviewId = reviewService.saveReview(STORE_ID, reviewDto, new ArrayList<>());

        // then
        assertEquals(reviewId,REVIEW_ID);

    }
}