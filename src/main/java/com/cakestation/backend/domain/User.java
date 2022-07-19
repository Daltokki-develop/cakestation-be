package com.cakestation.backend.domain;

import com.cakestation.backend.service.dto.response.KakaoUserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
