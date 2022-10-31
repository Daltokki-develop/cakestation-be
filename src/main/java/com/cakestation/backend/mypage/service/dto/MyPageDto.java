package com.cakestation.backend.mypage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDto {

    private String nickName;
    private int reviewCount;
    private int reviewImageCount;
    private int likeCount;

}
