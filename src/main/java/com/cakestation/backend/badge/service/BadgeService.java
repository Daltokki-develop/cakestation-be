package com.cakestation.backend.badge.service;


import com.cakestation.backend.badge.domain.Badge;
import com.cakestation.backend.badge.domain.Badge_User;
import com.cakestation.backend.badge.repository.BadgeRepository;
import com.cakestation.backend.badge.repository.Badge_UserRepository;
import com.cakestation.backend.badge.service.dto.response.ProvideBage;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.domain.User;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    private final UserRepository userRepository;

    private final Badge_UserRepository badge_userRepository;
    // - 공통 기능
    // 조건 충족시 배지를 (임의)부여하는 기능
    @Transactional(readOnly = false)
    public ProvideBage providebadge() {

        // TODO 유저정보와 배지정보 받는것으로 변경

        User userInfo = userRepository.findById(1L).get();

        Long testBadge = 1L; //테스트를 위해서 배지 아이디를 1로 지정
        Badge Getbadge = badgeRepository.findById(testBadge).get();

        Badge_User relationBadge = badge_userRepository.save(Badge_User.builder()
                .user(userInfo)
                .badge(Getbadge)
                .build());

        Getbadge.getUserList().add(relationBadge);
        userInfo.getBadgeList().add(relationBadge);
        userRepository.save(userInfo);
        badgeRepository.save(Getbadge);

        ProvideBage provideBage = ProvideBage.builder()
                                                .badgename(Getbadge.getBadgename())
                                                .username(userInfo.getUsername())
                                                .misson(Getbadge.getMission())
                                                .build();
                                                
        return provideBage;
    
    }


    // - 관리자 기능
    //배지를 삭제시킬수 있는 기능
    //배지를 임의로 부여 할 수 있는 기능

    // - 유저 기능
    // 보유한 전체 배지를 조회하는 기능
    

    // - 확인 사항
    //예를 들어 가게 등록 배지일 경우 배지 획득을 위해 가게를 무작위로 등록하고 배지
    //획득 후 가게를 삭제한다면?

}
