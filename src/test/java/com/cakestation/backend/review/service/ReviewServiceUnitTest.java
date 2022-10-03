package com.cakestation.backend.review.service;

import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.ReviewImageDto;
import com.cakestation.backend.store.repository.StoreRepository;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static com.cakestation.backend.review.fixture.ReviewFixture.*;
import static com.cakestation.backend.store.fixture.StoreFixture.STORE_ID;
import static com.cakestation.backend.store.fixture.StoreFixture.storeEntity;
import static com.cakestation.backend.user.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    @Mock
    ImageUploadService imageUploadService;
    @InjectMocks
    ReviewServiceImpl reviewService;

    @Test
    void 리뷰_등록() {
        // given
        doReturn(Optional.of(getUserEntity())).when(userRepository).findUserByEmail(any());
        doReturn(Optional.of(storeEntity())).when(storeRepository).findById(any());
        doReturn(reviewEntity()).when(reviewRepository).save(any());
        doReturn(new ArrayList<String>()).when(imageUploadService).uploadFiles(IMAGES);

        // when
        Long reviewId = reviewService.saveReview(getCreateReviewDto(), getKakaoUserDto().getEmail());

        // then
        assertEquals(reviewId, REVIEW_ID);
    }

    @Test
    void 리뷰_단일_조회() {
        // given
        doReturn(Optional.of(reviewEntity())).when(reviewRepository).findById(any());
        // when
        ReviewDto reviewDto = reviewService.findReviewById(REVIEW_ID);
        // then
        assertEquals(REVIEW_ID,reviewDto.getReviewId());

    }

    @Test
    void 리뷰_조회_BY_작성자() {
        // given
        doReturn(Collections.singletonList(reviewEntity())).when(reviewRepository).findAllByWriter(any(), any());

        // when
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<ReviewDto> reviewDtoList = reviewService.findReviewsByWriter(USER_ID, pageRequest);

        // then
        assertEquals(USERNAME, reviewDtoList.get(0).getUsername());
    }

    @Test
    void 리뷰_조회_BY_가게() {
        // given
        doReturn(Collections.singletonList(reviewEntity())).when(reviewRepository).findAllByStore(any(), any());

        // when
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<ReviewDto> reviewDtoList = reviewService.findReviewsByStore(STORE_ID, pageRequest);

        // then
        assertEquals(USERNAME, reviewDtoList.get(0).getUsername());
    }

    @Test
    void 리뷰_이미지_조회_BY_가게() {
        // given
        doReturn(Collections.singletonList(reviewEntity())).when(reviewRepository).findAllByStore(any(), any());

        // when
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<ReviewImageDto> reviewImageDtoList = reviewService.findReviewImagesByStore(STORE_ID, pageRequest);

        // then
        assertEquals(reviewImageDtoList.size(), IMAGE_URLS.size());
    }
}