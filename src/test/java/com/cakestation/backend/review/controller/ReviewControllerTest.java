package com.cakestation.backend.review.controller;


import com.cakestation.backend.review.controller.dto.request.CreateReviewRequest;
import com.cakestation.backend.review.fixture.ReviewFixture;
import com.cakestation.backend.review.service.ReviewService;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import com.cakestation.backend.store.service.StoreService;
import com.cakestation.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.transaction.Transactional;

import static com.cakestation.backend.store.fixture.StoreFixture.STORE_ID;
import static com.cakestation.backend.store.fixture.StoreFixture.getStoreDto;
import static com.cakestation.backend.user.fixture.UserFixture.USER_ID;
import static com.cakestation.backend.user.fixture.UserFixture.getKakaoUserDto;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
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
    public void HTTP_리뷰_등록() throws Exception {

        //given
        // 회원 등록
        userService.join(getKakaoUserDto());

        // 가게 등록
        storeService.saveStore(getStoreDto());


        CreateReviewRequest createReviewRequest = ReviewFixture.getCreateReviewRequest();
        CreateReviewDto createReviewDto = createReviewRequest.toServiceDto(STORE_ID, createReviewRequest);

        reviewService.saveReview(createReviewDto,getKakaoUserDto().getEmail());

        String uri = String.format("/api/stores/%d/reviews",STORE_ID);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void HTTP_리뷰_조회_by_작성자() throws Exception {

        //given
        // 회원 등록
        userService.join(getKakaoUserDto());

        // 가게 등록
        storeService.saveStore(getStoreDto());

        // 리뷰 등록
        CreateReviewRequest createReviewRequest = ReviewFixture.getCreateReviewRequest();
        CreateReviewDto createReviewDto = createReviewRequest.toServiceDto(STORE_ID, createReviewRequest);

        reviewService.saveReview(createReviewDto,getKakaoUserDto().getEmail());

        String uri = String.format("/api/users/%d/reviews",USER_ID);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void HTTP_리뷰_조회_by_가게() throws Exception {

        //given
        // 회원 등록
        userService.join(getKakaoUserDto());

        // 가게 등록
        storeService.saveStore(getStoreDto());

        // 리뷰 등록
        CreateReviewRequest createReviewRequest = ReviewFixture.getCreateReviewRequest();
        CreateReviewDto createReviewDto = createReviewRequest.toServiceDto(STORE_ID, createReviewRequest);
        reviewService.saveReview(createReviewDto,getKakaoUserDto().getEmail());

        String uri = String.format("/api/stores/%d/reviews",STORE_ID);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void HTTP_리뷰이미지_조회_by_가게() throws Exception {

        //given
        // 회원 등록
        userService.join(getKakaoUserDto());

        // 가게 등록
        storeService.saveStore(getStoreDto());

        // 리뷰 등록
        CreateReviewRequest createReviewRequest = ReviewFixture.getCreateReviewRequest();
        CreateReviewDto createReviewDto = createReviewRequest.toServiceDto(STORE_ID, createReviewRequest);
        reviewService.saveReview(createReviewDto,getKakaoUserDto().getEmail());

        String uri = String.format("/api/stores/%d/reviewImages",STORE_ID);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}