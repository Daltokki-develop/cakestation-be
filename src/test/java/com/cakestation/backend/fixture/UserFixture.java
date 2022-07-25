package com.cakestation.backend.fixture;

import com.cakestation.backend.domain.Role;
import com.cakestation.backend.domain.User;
import com.cakestation.backend.service.dto.response.KakaoUserDto;

public class UserFixture {
    public static String username = "song2"; // 닉네임
    public static String email = "song2@gmail.com";

    public static KakaoUserDto getKakaoUserDto(){
        return KakaoUserDto.builder()
                .username(username)
                .email(email)
                .build();
    }

    public static User getUser(){
        return User.builder()
                .id(1L)
                .username(username)
                .email(email)
                .role(Role.ROLE_USER)
                .build();
    }
}
