package com.cakestation.backend.badge.controller;

import com.cakestation.backend.badge.service.BadgeService;
import com.cakestation.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.cakestation.backend.badge.fixture.BadgeFixture.getBadgeDto;
import static com.cakestation.backend.user.fixture.UserFixture.getKakaoUserDto;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BadgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private UserService userService;

    @Test
    public void 뱃지_조회() throws Exception{

     userService.join(getKakaoUserDto());

//     badgeService.
    }
}
