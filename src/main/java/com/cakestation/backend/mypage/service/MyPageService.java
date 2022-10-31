package com.cakestation.backend.mypage.service;

import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.mypage.service.dto.MyPageDto;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.repository.ReviewRepository;
import com.cakestation.backend.user.domain.User;
import com.cakestation.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public MyPageDto getMyPageInfo(String currentEmail) {
        User user = userRepository.findUserByEmail(currentEmail).orElseThrow(
                () -> new IdNotFoundException("사용자를 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findAllByWriter(user.getId());
        int reviewImageCount = reviews.stream().mapToInt(review -> review.getImageUrls().size()).sum();

        int randomNumber = getRandomNumber();

        //TODO(좋아요 기능 구현시 연결 필요)
        return new MyPageDto(user.getNickname(), reviews.size(), reviewImageCount, 0, randomNumber);
    }

    private int getRandomNumber() {
        return (int) (Math.random() * 4) + 1;
    }
}
