package com.cakestation.backend.user.service;

import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.cakestore.repository.LikeStoreRepository;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.domain.NicknameType;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import com.cakestation.backend.user.service.dto.response.KakaoUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final LikeStoreRepository likeStoreRepository;

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

    @Transactional
    public void deleteUser(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));

        likeStoreRepository.deleteLikeStoresByIds(getLikeStoreIds(user.getId()));

        List<Long> reviewIds = getReviewIdsByWriter(user.getId());

        reviewRepository.deleteReviewImagesByReviewIds(reviewIds);
        reviewRepository.deleteReviewTagsByReviewIds(reviewIds);
        reviewRepository.deleteReviewByIds(reviewIds);

        userRepository.deleteById(user.getId());
    }

    private List<Long> getLikeStoreIds(Long userId) {

        return likeStoreRepository.findAllByUser(userId)
                .stream()
                .map(LikeStore::getId)
                .collect(Collectors.toList());
    }

    public String getUniqueNickname() {
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

    private boolean validateNicknameExists(String newNickname) {

        return userRepository.findByNickname(newNickname).isPresent();
    }

    private User getUser(String userEmail) {

        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
    }

    private List<Long> getReviewIdsByWriter(Long userId) {

        return reviewRepository.findAllByWriterId(userId, Pageable.unpaged())
                .stream()
                .map(Review::getId)
                .collect(Collectors.toList());
    }
}

