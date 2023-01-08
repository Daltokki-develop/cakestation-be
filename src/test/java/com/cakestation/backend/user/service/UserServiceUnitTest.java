package com.cakestation.backend.user.service;

import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cakestation.backend.user.fixture.UserFixture.getUserEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UtilService utilService;

    @Mock
    UserService userService;

    @Test
    @DisplayName("User의 닉네임을 랜덤으로 부여 한다.")
    void 유저_닉네임_부여() {

        // given
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(getUserEntity()));
        String beforeNickname = userService.makeNickname();

        // when
        getUserEntity().setNickname(beforeNickname);

        // then
        assertNotNull(getUserEntity().getNickname());

    }

    @Test
    @DisplayName("User의 닉네임을 랜덤으로 재부여 한다.")
    void 유저_닉네임_재할당() {

        // given
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(getUserEntity()));
        String beforeNickname = userService.makeNickname();
        getUserEntity().setNickname(beforeNickname);

        // when
        String newNickname = userService.updateNickname(getUserEntity().getEmail());

        // when Error
        assertThrows(InvalidUserException.class , () -> {
            userService.updateNickname("ERROR@Email.com");
        });

        // then
        assertNotEquals(newNickname , getUserEntity().getNickname());

    }


}
