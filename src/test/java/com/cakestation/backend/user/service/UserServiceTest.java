package com.cakestation.backend.user.service;

import com.cakestation.backend.common.annotations.ServiceTest;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.cakestation.backend.user.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("UserService 는")
@ServiceTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach() {
        reviewRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("User의 닉네임을 랜덤으로 부여 한다.")
    void makeNickname() {
        User user = userRepository.save(new User(null, USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));

        assertThat(user.getNickname()).isNotNull();
    }

    @Test
    @DisplayName("User의 닉네임을 랜덤으로 재부여 한다.")
    void remakeNickname() {
        // given
        User user = userRepository.save(new User(null, USERNAME, NICKNAME, EMAIL, RANDOM_NUMBER, ROLE));
        String beforeNickname = user.getNickname();

        // when
        String newNickname = userService.updateNickname(user.getEmail());

//        // when Error
//        assertThrows(InvalidUserException.class, () -> {
//            userService.updateNickname("ERROR@Email.com");
//        });

        // then
        assertThat(newNickname).isNotEqualTo(beforeNickname);
    }
}
