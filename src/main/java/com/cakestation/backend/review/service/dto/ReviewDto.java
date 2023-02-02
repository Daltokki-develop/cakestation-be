package com.cakestation.backend.review.service.dto;

import com.cakestation.backend.review.domain.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;
    private Long storeId;
    private Long userId;
    private String nickname;
    private int cakeNumber;
    private int score;
    private String sheetType;
    private String requestOption;
    private DesignSatisfaction designSatisfaction;
    private List<String> reviewImages;
    private List<Tag> tags;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;

    public static ReviewDto from(Review review, List<Tag> tags, List<String> imageUrls) {

        return ReviewDto.builder()
                .reviewId(review.getId())
                .storeId(review.getCakeStore().getId())
                .nickname(review.getWriter().getNickname())
                .cakeNumber(review.getCakeNumber())
                .score(review.getScore())
                .sheetType(review.getSheetType())
                .requestOption(review.getRequestOption())
                .designSatisfaction(review.getDesignSatisfaction())
                .content(review.getContent())
                .reviewImages(imageUrls)
                .tags(tags)
                .createdDateTime(review.getCreatedDateTime())
                .lastModifiedDateTime(review.getLastModifiedDateTime())
                .build();
    }
}
