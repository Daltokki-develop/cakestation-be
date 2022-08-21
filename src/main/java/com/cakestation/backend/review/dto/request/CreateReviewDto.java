package com.cakestation.backend.review.dto.request;

import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Distance;
import com.cakestation.backend.review.domain.Tag;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateReviewDto{

    private Long storeId; // 가게 ID
    private List<MultipartFile> reviewImages; // 리뷰 사진 file
    private List<String> imageUrls = new ArrayList<>(); // 리뷰 사진 url
    private String nearByStation; // 가장 가까운 역
    private Distance walkingDistance; // 도보 거리 (5, 10, 15, 15이상)
    private int cakeNumber; // 케이크 호수
    private String sheetType; // 시트 종류
    private String requestOption; // 추가 옵션
    private DesignSatisfaction designSatisfaction; // 만족도
    private int score; // 별점
    private List<Tag> tags;
    private String content; // 하고 싶은 말

}
