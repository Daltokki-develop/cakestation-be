package com.cakestation.backend.service.dto.request;


import com.cakestation.backend.domain.Store;
import com.cakestation.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CreateStoreDto {
    private Long userId;

    private String name;

    private String address;

    private String businessHours;

    private String phone;

    private String photoUrl;

    private String webpageUrl;

    private String kakaoMapUrl;

    private Float score;

    public static Store toEntity(User user, CreateStoreDto createStoreDto){
        return Store.builder()
                .user(user)
                .name(createStoreDto.getName())
                .address(createStoreDto.getAddress())
                .businessHours(createStoreDto.getBusinessHours())
                .phone(createStoreDto.getPhone())
                .photoUrl(createStoreDto.getPhotoUrl())
                .webpageUrl(createStoreDto.getWebpageUrl())
                .kakaoMapUrl(createStoreDto.getKakaoMapUrl())
                .score(0.0)
                .numOfReviews(0)
                .uploadDate(new Date())
                .build();
    }
}
