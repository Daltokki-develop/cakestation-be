package com.cakestation.backend.badge.service.dto.request;

import com.cakestation.backend.badge.domain.Badge;
import com.cakestation.backend.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CreateBadgeDto {
    private String badgename;

    private String mission;

    public static CreateBadgeDto toEntity(CreateBadgeDto createStoreDto){
        return CreateBadgeDto.builder()
                .badgename(createStoreDto.getBadgename())
                .mission(createStoreDto.getMission())
                .build();
    }
}
