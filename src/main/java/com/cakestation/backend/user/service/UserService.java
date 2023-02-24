package com.cakestation.backend.user.service;

import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.cakestore.repository.LikeStoreRepository;
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
import java.util.Random;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final LikeStoreRepository likeStoreRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long join(KakaoUserDto kakaoUserDto) {
        User user = userRepository.findUserByEmail(kakaoUserDto.getEmail())
                .orElseGet(() -> userRepository.save(User.createUser(kakaoUserDto, getUniqueNickname())));

        return user.getId();
    }

    @Transactional
    public String updateNickname(String userEmail) {
        User user = getUser(userEmail);
        user.setNickname(getUniqueNickname());

        return user.getNickname();
    }

    private String getUniqueNickname() {
        String newNickname;
        do {
            newNickname = makeNickname();
        } while (validateNicknameExists(newNickname));
        return newNickname;
    }

    public static String makeNickname() {
        List<String> fruits = NicknameType.RADIX.getKeywords();
        List<String> actions = NicknameType.PREFIX.getKeywords();

        Random random = new Random();

        return actions.get(random.nextInt(actions.size())) + fruits.get(random.nextInt(fruits.size()));
    }

    @Transactional
    public void deleteUser(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
        likeStoreRepository.deleteAll(likeStoreRepository.findLikeStoresByUser(user));
        userRepository.deleteById(user.getId());
    }

    private boolean validateNicknameExists(String newNickname) {
        return userRepository.findByNickname(newNickname).isPresent();
    }

    private User getUser(String userEmail) {
        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
    }
}

