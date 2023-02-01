package com.cakestation.backend.common;

import com.cakestation.backend.cakestore.controller.CakeStoreController;
import com.cakestation.backend.cakestore.service.CakeStoreQueryService;
import com.cakestation.backend.cakestore.service.CakeStoreService;
import com.cakestation.backend.review.controller.ReviewController;
import com.cakestation.backend.review.service.ReviewQueryService;
import com.cakestation.backend.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(value = {CakeStoreController.class, ReviewController.class}
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
    protected UtilService utilService;
    @MockBean
    protected ReviewService reviewService;
    @MockBean
    protected ReviewQueryService reviewQueryService;
    @Autowired
    protected WebApplicationContext ctx;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
}
