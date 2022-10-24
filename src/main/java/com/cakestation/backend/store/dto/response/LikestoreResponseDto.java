package com.cakestation.backend.store.dto.response;

import com.cakestation.backend.store.domain.CakeStore;
import com.cakestation.backend.user.domain.User;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class LikestoreResponseDto {

    private Long storeId;
    private Long userId;
    private String userEmail;
    private String storeName;

    public static LikestoreResponseDto createLikeStoreDto(User user , CakeStore cakeStore){
        return LikestoreResponseDto.builder()
                .storeId(cakeStore.getId())
                .userId(user.getId())
                .storeName(cakeStore.getName())
                .userEmail(user.getEmail())
                .build();
    }
}
