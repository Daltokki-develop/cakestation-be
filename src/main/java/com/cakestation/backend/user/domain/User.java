package com.cakestation.backend.user.domain;

import com.cakestation.backend.common.domain.BaseEntity;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.*;

import javax.persistence.*;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    private int randomNumber;

    @Builder
    public User(String username, String nickname, String email, int randomNumber, Role role) {
        this.id = null;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.randomNumber = randomNumber;
        this.role = role;
    }

    public static User createUser(KakaoUserDto kakaoUserDto, String nickname) {
        return User.builder()
                .username(kakaoUserDto.getUsername())
                .email(kakaoUserDto.getEmail())
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .randomNumber(createRandomNumber())
                .build();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private static int createRandomNumber() {
        Random random = new Random();
        return random.nextInt(4);
    }
}
