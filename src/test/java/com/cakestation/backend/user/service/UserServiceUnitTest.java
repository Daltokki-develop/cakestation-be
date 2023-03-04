package com.cakestation.backend.user.service;

import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cakestation.backend.user.fixture.UserFixture.getUserEntity;
import static com.cakestation.backend.user.service.UserService.makeNickname;
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
    @DisplayName("User의 닉네임을 랜덤으로 부여할 수 있다.")
    void create_user_nickname_as_random() {
        // given, when
        String nickname = makeNickname();

        // then
        assertThat(nickname).isNotNull();
    }

//    @Test
//    @DisplayName("User의 닉네임을 업데이트할 수 있다.")
//    void recreate_user_nickname_as_random() {
//        System.out.println("==========여기서부터 시작===========");
//        // given
//        User user = getUserEntity();
//
//        doReturn(Optional.of(user)).when(userRepository).findUserByEmail(any());
//        doReturn(Optional.empty()).when(userRepository).findByNickname(any());
//
//        // when
//        String newNickname = userService.updateNickname(user.getEmail());
//
//        // then
//        assertThat(newNickname).isNotEqualTo(user.getNickname());
//    }
}
