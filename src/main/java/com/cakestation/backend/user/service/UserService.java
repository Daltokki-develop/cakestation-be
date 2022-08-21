package com.cakestation.backend.user.service;

import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.dto.response.KakaoUserDto;
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

        Optional<User> findUser = userRepository.findUserByEmail(kakaoUserDto.getEmail());

        // 이미 회원이 존재하는 경우
        if (findUser.isPresent()) {
            return findUser.get().getId();
        }
        // 회원 정보가 없는 경우
        else {
            // 사용자 생성
            User user = User.createUser(kakaoUserDto);
            return userRepository.save(user).getId();
        }
    }
    
//    public Long getUserId(KakaoUserDto kakaoUserDto) {
//    User targetUser = null;
//
//    try{
//        targetUser = userRepository.findUserByEmail(kakaoUserDto.getEmail());
//    }catch(NullPointerException e){
//        e.printStackTrace();
//    }
//    return targetUser.getId();
//
//    }

}
