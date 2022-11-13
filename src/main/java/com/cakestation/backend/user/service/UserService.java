package com.cakestation.backend.user.service;

import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.user.domain.Nickname.NicknameType;
import com.cakestation.backend.user.domain.User;
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

        Optional<User> findUser = Optional.of(userRepository.findUserByEmail(kakaoUserDto.getEmail())
                .orElseGet(() -> userRepository.save(User.createUser(kakaoUserDto))));

        return findUser.get().getId();

    }

    @Transactional
    public String getNickname(String userEmail, boolean reNickname) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new IdNotFoundException("찾을수 없는 유저입니다."));

        String nickname;
        if (user.getNickname().isEmpty() || reNickname) {
            nickname = makeNickname();
            user.updateNickname(nickname);
        } else {
            nickname = user.getNickname();
        }
        return nickname;
    }

    private String makeNickname() {
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
        //유저 닉네임 저장
        return resultName;
    }
}

