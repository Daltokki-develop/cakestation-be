package com.cakestation.backend.user.domain;

import java.sql.Timestamp;
import java.util.List;

import com.cakestation.backend.review.domain.Review;

import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(nullable = false, unique = true)
    private String email;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

//    // fetch 전략은 기본이 LAZY이며, 필요에 따라 EAGER로 바꿈
//    @OneToMany(mappedBy = "user") // , fetch = FetchType.EAGER)
//    private List<Review> reviews;

    public static User createUser(KakaoUserDto kakaoUserDto){
        return User.builder()
                .username(kakaoUserDto.getUsername())
                .email(kakaoUserDto.getEmail())
                .role(Role.ROLE_USER)
                .build();
    }
}
