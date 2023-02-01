package com.cakestation.backend.review.controller;


import com.cakestation.backend.common.dto.ApiResponse;
import com.cakestation.backend.common.ControllerTest;
import com.cakestation.backend.common.auth.AuthUtil;
import com.cakestation.backend.review.controller.dto.request.UpdateReviewRequest;
import com.cakestation.backend.review.controller.dto.response.ReviewResponse;
import com.cakestation.backend.review.domain.Tag;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.cakestation.backend.review.service.dto.UpdateReviewDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static com.cakestation.backend.cakestore.fixture.StoreFixture.STORE_ID_1;
import static com.cakestation.backend.common.auth.AuthUtil.getCurrentUserEmail;
import static com.cakestation.backend.review.fixture.ReviewFixture.getCreateReviewRequest;
import static com.cakestation.backend.review.fixture.ReviewFixture.getReviewEntity;
import static com.cakestation.backend.user.fixture.UserFixture.EMAIL;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReviewController 는")
class ReviewControllerTest extends ControllerTest {

    @DisplayName("리뷰를 등록할 수 있다.")
    @Test
    public void uploadReview() throws Exception {
        given(getCurrentUserEmail()).willReturn(EMAIL);
        given(reviewService.saveReview(any(CreateReviewDto.class), anyString())).willReturn(1L);

        String uri = String.format("/api/stores/%d/reviews", STORE_ID_1);

        ApiResponse<Long> expectedResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(), 1L);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(uri)
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getCreateReviewRequest()))
                                .header("Authorization", "abc")
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(expectedResponse))
                )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @DisplayName("리뷰를 수정할 수 있다.")
    @Test
    public void updateReview() throws Exception {

        ReviewDto reviewDto = ReviewDto.from(getReviewEntity(), List.of(Tag.CHEAP), List.of());
        given(reviewService.updateReview(any(UpdateReviewDto.class), anyLong()))
                .willReturn(reviewDto);

        String uri = String.format("/api/reviews/%d", STORE_ID_1);

        ApiResponse<ReviewResponse> expectedResponse = new ApiResponse<>(
                HttpStatus.OK.value(), ReviewResponse.from(reviewDto));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.patch(uri)
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new UpdateReviewRequest()))
                )
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(expectedResponse))
                )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}