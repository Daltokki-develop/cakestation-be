package com.cakestation.backend.service;

import com.cakestation.backend.domain.User;
import com.cakestation.backend.repository.UserRepository;
import com.cakestation.backend.service.dto.response.KakaoUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(KakaoUserDto kakaoUserDto) {

        User findUser = userRepository.findUserByEmail(kakaoUserDto.getEmail());

        System.out.println(findUser+"!!!!!");
        // 이미 회원이 존재하는 경우
        if (findUser != null) {
            return findUser.getId();
        }
        // 회원 정보가 없는 경우
        else {
            // 사용자 생성
            User user = User.createUser(kakaoUserDto);
            return userRepository.save(user).getId();
        }

    }
}
