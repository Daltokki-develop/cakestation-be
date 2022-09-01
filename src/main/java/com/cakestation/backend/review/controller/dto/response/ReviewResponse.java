package com.cakestation.backend.review.controller.dto.response;

import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.Tag;
import com.cakestation.backend.review.service.dto.ReviewDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private String username;
    private int cakeNumber;
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
                .username(reviewDto.getUsername())
                .cakeNumber(reviewDto.getCakeNumber())
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
