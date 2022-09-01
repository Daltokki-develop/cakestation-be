package com.cakestation.backend.badge.service.dto.response;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ProvideBage {
    
    private String username; // 획득한 유저이름 

    private String badgename; // 뱃지이름

    private String misson; //뱃지 획득 조건

}
