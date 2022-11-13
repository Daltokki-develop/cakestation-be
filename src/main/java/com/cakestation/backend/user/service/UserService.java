package com.cakestation.backend.user.service;

import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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
}

