package com.cakestation.backend.badge.service;


import com.cakestation.backend.badge.domain.Badge;
import com.cakestation.backend.badge.repository.BadgeRepository;
import com.cakestation.backend.badge.repository.Badge_UserRepository;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.cakestation.backend.badge.fixture.BadgeFixture.*;
import static com.cakestation.backend.user.fixture.UserFixture.getUserEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BadgeServiceUnitTest {

    @Mock
    BadgeRepository badgeRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Badge_UserRepository badge_userRepository;

    @InjectMocks
    BadgeService badgeService;

    @Test
    void 뱃지_등록() {
        doReturn(Optional.of(getUserEntity())).when(userRepository).findUserByEmail(any());
        doReturn(Optional.of(badgeEntity())).when(badgeRepository).findById(any());
        doReturn(new ArrayList<String>()).when(badge_userRepository).save(any());
    }

    @Test
    public void 뱃지_조회() {

        Badge mockedBadge = Badge.builder()
                .id(BADGE_ID)
                .build();
        when(badgeRepository.findById(BADGE_ID)).thenReturn(Optional.ofNullable(mockedBadge));

//        Badge badge = badgeService.
    }

}
