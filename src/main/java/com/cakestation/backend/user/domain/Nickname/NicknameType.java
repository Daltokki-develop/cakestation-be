package com.cakestation.backend.user.domain.Nickname;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum NicknameType {
    PREFIX(Arrays.asList("포효하는 ", "울음많은 ", "화가많은 ", "고민많은 ", "소심한 ", "웃음많은 ", "어지러운 ",
            "말이많은 ", "걱정많은 ", "친구많은 ", "친구없는 ", "차가운 ", "냉정한 ", "계획적인 ")),
    RADIX(Arrays.asList("체리", "망고", "복숭아", "오렌지", "사과", "바나나", "두리안", "메론"
            , "수박", "딸기", "망고스틴", "키위", "올리브", "참외", "블루베리", "파인애플", "딸기"
            , "라즈베리", "두리안", "청사과"));

    private final List<String> keywords;

    NicknameType(List<String> keywords) {
        this.keywords = keywords;
    }
}
