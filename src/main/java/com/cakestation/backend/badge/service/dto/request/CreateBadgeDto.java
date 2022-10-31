package com.cakestation.backend.badge.service.dto.request;

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
