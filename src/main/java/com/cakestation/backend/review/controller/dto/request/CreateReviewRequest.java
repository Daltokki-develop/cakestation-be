package com.cakestation.backend.review.controller.dto.request;

import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Tag;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateReviewRequest {

    private List<String> reviewImages; // 리뷰 이미지 base64 리스트
    private int cakeNumber; // 케이크 호수
    private String sheetType; // 시트 종류
    private String requestOption; // 추가 옵션
    private DesignSatisfaction designSatisfaction; // 만족도
    private int score; // 별점
    private List<Tag> tags;
    private String content; // 하고 싶은 말

    @Builder
    public CreateReviewRequest(int cakeNumber, String sheetType, String requestOption, DesignSatisfaction designSatisfaction, int score, List<Tag> tags, String content) {
        this.reviewImages = new ArrayList<>();
        this.cakeNumber = cakeNumber;
        this.sheetType = sheetType;
        this.requestOption = requestOption;
        this.designSatisfaction = designSatisfaction;
        this.score = score;
        this.tags = tags;
        this.content = content;
    }

    public CreateReviewDto toServiceDto(Long storeId, CreateReviewRequest createReviewRequest) {
        return CreateReviewDto.builder()
                .storeId(storeId)
                .reviewImages(createReviewRequest.getReviewImages())
                .cakeNumber(createReviewRequest.getCakeNumber())
                .sheetType(createReviewRequest.getSheetType())
                .requestOption(createReviewRequest.getRequestOption())
                .designSatisfaction(createReviewRequest.getDesignSatisfaction())
                .score(createReviewRequest.getScore())
                .tags(createReviewRequest.getTags())
                .content(createReviewRequest.getContent())
                .build();
    }
}
