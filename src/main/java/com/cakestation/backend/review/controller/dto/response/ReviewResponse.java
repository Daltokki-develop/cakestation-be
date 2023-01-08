package com.cakestation.backend.review.controller.dto.response;

import com.cakestation.backend.review.domain.Tag;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long reviewId;
    private String nickname;
    private int cakeNumber;
    private int score;
    private String sheetType;
    private String requestOption;
    private List<String> reviewImages;
    private List<Tag> tags;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedDateTime;

    public static ReviewResponse from(ReviewDto reviewDto){
        return ReviewResponse.builder()
                .reviewId(reviewDto.getReviewId())
                .nickname(reviewDto.getNickname())
                .cakeNumber(reviewDto.getCakeNumber())
                .score(reviewDto.getScore())
                .sheetType(reviewDto.getSheetType())
                .requestOption(reviewDto.getRequestOption())
                .reviewImages(reviewDto.getReviewImages())
                .content(reviewDto.getContent())
                .tags(reviewDto.getTags())
                .createdDateTime(reviewDto.getCreatedDateTime())
                .lastModifiedDateTime(reviewDto.getLastModifiedDateTime())
                .build();
    }
}
