package com.cakestation.backend.review.controller;


import com.cakestation.backend.review.dto.request.CreateReviewRequest;
import com.cakestation.backend.review.fixture.ReviewFixture;
import com.cakestation.backend.review.service.ReviewService;
import com.cakestation.backend.review.dto.request.CreateReviewDto;
import com.cakestation.backend.store.service.StoreService;
import com.cakestation.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.cakestation.backend.store.fixture.StoreFixture.STORE_ID;
import static com.cakestation.backend.store.fixture.StoreFixture.getStoreDto;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Test
    public void 리뷰_등록() throws Exception {

        //given
        // 회원 등록
        // TODO()
        // 가게 등록
        storeService.saveStore(getStoreDto());


        CreateReviewRequest createReviewRequest = ReviewFixture.getCreateReviewRequest();
        CreateReviewDto createReviewDto = createReviewRequest.toServiceDto(STORE_ID, createReviewRequest);

        reviewService.saveReview(createReviewDto);

        String uri = String.format("/stores/%d/reviews",STORE_ID);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}