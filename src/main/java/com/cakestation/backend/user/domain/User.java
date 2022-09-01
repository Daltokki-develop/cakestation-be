package com.cakestation.backend.user.domain;

import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public static User createUser(KakaoUserDto kakaoUserDto){
        return User.builder()
                .username(kakaoUserDto.getUsername())
                .email(kakaoUserDto.getEmail())
                .role(Role.ROLE_USER)
                .build();
    }
}
