package com.cakestation.backend.badge.fixture;

import com.cakestation.backend.badge.domain.Badge;
import com.cakestation.backend.badge.service.dto.request.CreateBadgeDto;

public class BadgeFixture {

    public static Long BADGE_ID = 1L;

    public static String BADGENAME = "콜럼버스 뱃지";

    public static String MISSON = "처음으로 가게를 찾아 등록하셨습니다.";

    public static Badge badgeEntity(){
        return Badge.builder()
                .id(BADGE_ID)
                .badgename(BADGENAME)
                .mission(MISSON)
                .build();
    }

    public static CreateBadgeDto getBadgeDto(){
        return CreateBadgeDto.builder()
                .badgename(BADGENAME)
                .mission(MISSON)
                .build();
    }
}
