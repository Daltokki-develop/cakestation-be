package com.cakestation.backend.user.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private int accessExpires;
    private int refreshExpires;
}
