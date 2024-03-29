package com.cakestation.backend.common;

import com.cakestation.backend.cakestore.controller.CakeStoreController;
import com.cakestation.backend.cakestore.service.CakeStoreQueryService;
import com.cakestation.backend.cakestore.service.CakeStoreService;
import com.cakestation.backend.common.auth.AuthUtil;
import com.cakestation.backend.common.config.KakaoConfig;
import com.cakestation.backend.mypage.controller.MyPageController;
import com.cakestation.backend.mypage.service.MyPageService;
import com.cakestation.backend.review.controller.ReviewController;
import com.cakestation.backend.review.service.ReviewQueryService;
import com.cakestation.backend.review.service.ReviewService;
import com.cakestation.backend.user.controller.UserController;
import com.cakestation.backend.user.service.KakaoService;
import com.cakestation.backend.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = {CakeStoreController.class,
        ReviewController.class, MyPageController.class, UserController.class}
        , excludeAutoConfiguration = {SecurityAutoConfiguration.class})

public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected CakeStoreService cakeStoreService;
    @MockBean
    protected CakeStoreQueryService cakeStoreQueryService;
    @MockBean
    protected AuthUtil authUtil;
    @MockBean
    protected ReviewService reviewService;
    @MockBean
    protected ReviewQueryService reviewQueryService;
    @MockBean
    protected MyPageService myPageService;
    @MockBean
    protected UserService userService;
    @MockBean
    protected KakaoService kakaoService;
    @MockBean
    protected KakaoConfig kakaoConfig;
    @Autowired
    protected WebApplicationContext ctx;

    private static MockedStatic<AuthUtil> utilService;

    @BeforeAll
    public static void beforeAll() {
        utilService = mockStatic(AuthUtil.class);
    }

    @AfterAll
    public static void afterAll() {
        utilService.close();
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
}
