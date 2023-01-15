package com.cakestation.backend.user.service;

import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.user.domain.NicknameType;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(KakaoUserDto kakaoUserDto) {

        User user = userRepository.findUserByEmail(kakaoUserDto.getEmail())
                .orElseGet(() -> userRepository.save(
                        User.createUser(kakaoUserDto, makeNickname())));
        return user.getId();
    }

    @Transactional
    public String updateNickname(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));

        String nickname = makeNickname();
        user.setNickname(nickname);

        return nickname;
    }

    public String makeNickname() {
        List<String> fruits = NicknameType.RADIX.getKeywords();
        List<String> actions = NicknameType.PREFIX.getKeywords();

        Random random = new Random();

        String resultName = actions.get(random.nextInt(actions.size())) + fruits.get(random.nextInt(fruits.size()));

        //닉네임 중복여부 확인하여 입력
        Optional<User> checkNickName;
        checkNickName = userRepository.findByNickname(resultName);

        //DB 안에 같은 데이터가 있으면 계속 새로운 닉네임을 만들기
        while (checkNickName.isPresent()) {
            resultName = actions.get(random.nextInt(actions.size())) + fruits.get(random.nextInt(fruits.size()));
            checkNickName = userRepository.findByNickname(resultName);
        }
        return resultName;
    }

    public void deleteUser(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
        userRepository.delete(user);
    }
}

