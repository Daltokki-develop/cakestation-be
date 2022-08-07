package com.cakestation.backend.user.fixture;

import com.cakestation.backend.user.domain.Role;
import com.cakestation.backend.user.domain.User;

public class UserFixture {
    public static Long USER_ID = 1L;
    public static String USERNAME = "김송이";
    public static String EMAIL = "aa@gmail.com";
    public static Role ROLE = Role.ROLE_USER;

    public static User userEntity(){
        return User.builder()
                .id(USER_ID)
                .username(USERNAME)
                .email(EMAIL)
                .role(ROLE)
                .build();
    }
}
