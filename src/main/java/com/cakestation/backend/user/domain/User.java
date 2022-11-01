package com.cakestation.backend.user.domain;

import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    private int randomNumber;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    public static User createUser(KakaoUserDto kakaoUserDto) {
        return User.builder()
                .username(kakaoUserDto.getUsername())
                .email(kakaoUserDto.getEmail())
                .role(Role.ROLE_USER)
                .randomNumber(createRandomNumber())
                .build();
    }

    private static int createRandomNumber() {
        return (int) (Math.random() * 4) ;
    }

}
