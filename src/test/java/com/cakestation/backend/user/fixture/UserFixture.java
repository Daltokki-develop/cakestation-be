package com.cakestation.backend.user.fixture;

import com.cakestation.backend.user.domain.Role;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;

public class UserFixture {
    public static Long USER_ID = 1L;
    public static String USERNAME = "김송이";
    public static String EMAIL = "aa@gmail.com";
    public static String EMAIL_2 = "bb@gmail.com";

    public static String NICKNAME = "걱정많은 딸기";
    public static int RANDOM_NUMBER = 1;
    public static Role ROLE = Role.ROLE_USER;

    public static User getUserEntity() {
        return User.builder()
                .id(USER_ID)
                .nickname(NICKNAME)
                .randomNumber(RANDOM_NUMBER)
                .username(USERNAME)
                .email(EMAIL)
                .role(ROLE)
                .build();
    }

    public static KakaoUserDto getKakaoUserDto() {
        return KakaoUserDto.builder()
                .username(USERNAME)
                .email(EMAIL)
                .build();
    }
}
