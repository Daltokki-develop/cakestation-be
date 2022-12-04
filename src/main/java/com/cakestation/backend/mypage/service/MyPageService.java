package com.cakestation.backend.mypage.service;

import com.cakestation.backend.cakestore.repository.LikeStoreRepository;
import com.cakestation.backend.common.exception.ErrorType;
import com.cakestation.backend.mypage.service.dto.MyPageDto;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.exception.InvalidUserException;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final LikeStoreRepository likeStoreRepository;

    public MyPageDto getMyPageInfo(String currentEmail) {
        User user = userRepository.findUserByEmail(currentEmail).orElseThrow(
                () -> new InvalidUserException(ErrorType.NOT_FOUND_USER));

        List<Review> reviews = reviewRepository.findAllByWriter(user.getId());
        int reviewImageCount = getReviewImageCount(reviews);
        int likeCount = likeStoreRepository.findLikeStoresByUser(user).size();

        return MyPageDto.from(
                user.getNickname(), reviews.size(), reviewImageCount, likeCount, user.getRandomNumber());
    }

    private int getReviewImageCount(List<Review> reviews) {
        return reviews.stream().mapToInt(review -> review.getReviewImages().size()).sum();
    }
}
