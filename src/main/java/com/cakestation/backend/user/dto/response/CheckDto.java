package com.cakestation.backend.user.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckDto {

    private int userUid; // 기능이 적용된 사용자의 회원번호

}