package com.cakestation.backend.user.domain;

import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;


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

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<LikeStore> likeStore;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "writer")
    private List<Review> review;

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
        return (int) (Math.random() * 4);
    }

}
