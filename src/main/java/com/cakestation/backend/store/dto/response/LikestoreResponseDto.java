package com.cakestation.backend.store.dto.response;

import com.cakestation.backend.store.domain.Store;
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

    public static LikestoreResponseDto createLikeStoreDto(User user , Store store){
        return LikestoreResponseDto.builder()
                .storeId(store.getId())
                .userId(user.getId())
                .storeName(store.getName())
                .userEmail(user.getEmail())
                .build();
    }
}
