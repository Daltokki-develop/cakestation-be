package com.cakestation.backend.user.service;

import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cakestation.backend.user.fixture.UserFixture.getUserEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("User의 닉네임을 랜덤으로 부여 한다.")
    void 유저_닉네임_부여() {
        // given
        doReturn(Optional.empty()).when(userRepository).findByNickname(any());
        // when
        String nickname = userService.makeNickname();
        // then
        assertThat(nickname).isNotNull();
    }

    @Test
    @DisplayName("User의 닉네임을 랜덤으로 재부여 한다.")
    void 유저_닉네임_재할당() {

        // given
        doReturn(Optional.of(getUserEntity())).when(userRepository).findUserByEmail(any());
        doReturn(Optional.empty()).when(userRepository).findByNickname(any());
        String beforeNickname = userService.makeNickname();
        getUserEntity().setNickname(beforeNickname);

        // when
        String newNickname = userService.updateNickname(getUserEntity().getEmail());

//        // when Error
//        assertThrows(InvalidUserException.class, () -> {
//            userService.updateNickname("ERROR@Email.com");
//        });

        // then
        assertThat(newNickname).isNotEqualTo(getUserEntity().getNickname());
    }
}
