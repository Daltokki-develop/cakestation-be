package com.cakestation.backend.service.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserDto {

    private String username; // 닉네임
    private String email;
}
