package com.cakestation.backend.service.dto.request;


import com.cakestation.backend.domain.Store;
import com.cakestation.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CreateStoreDto {
    private Long userId;

    private String name;

    private Float score;

    private boolean isOpened;

    private String photoUrl;

    private String address;

    private String snsAddress;

    private String phone;

    public static Store toEntity(User user, CreateStoreDto createStoreDto){
        return Store.builder()
                .user(user)
                .name(createStoreDto.getName())
                .score(createStoreDto.getScore())
                .isOpened(createStoreDto.isOpened())
                .photoUrl(createStoreDto.getPhotoUrl())
                .address(createStoreDto.getAddress())
                .snsAddress(createStoreDto.getSnsAddress())
                .phone(createStoreDto.getPhone())
                .createdDate(new Date())
                .build();
    }
}
