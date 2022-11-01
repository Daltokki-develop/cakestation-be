package com.cakestation.backend.cakestore.controller.dto.response;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.user.domain.User;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class LikeStoreResponse {

    private Long storeId;
    private Long userId;
    private String userEmail;
    private String storeName;

    public static LikeStoreResponse createLikeStoreDto(User user , CakeStore cakeStore){
        return LikeStoreResponse.builder()
                .storeId(cakeStore.getId())
                .userId(user.getId())
                .storeName(cakeStore.getName())
                .userEmail(user.getEmail())
                .build();
    }
}
