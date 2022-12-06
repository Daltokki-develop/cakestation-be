package com.cakestation.backend.review.service.dto;

import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.ReviewImage;
import com.cakestation.backend.review.domain.ReviewTag;
import com.cakestation.backend.review.domain.Tag;
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
    private Long userId;
    private String username;
    private int cakeNumber;
    private int score;
    private String sheetType;
    private String requestOption;
    private List<String> reviewImages;
    private List<Tag> tags;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;

    public static ReviewDto from(Review review, List<Tag> tags, List<String> imageUrls) {

        return ReviewDto.builder()
                .reviewId(review.getId())
                .username(review.getWriter().getUsername())
                .cakeNumber(review.getCakeNumber())
                .score(review.getScore())
                .sheetType(review.getSheetType())
                .requestOption(review.getRequestOption())
                .content(review.getContent())
                .reviewImages(imageUrls)
                .tags(tags)
                .createdDateTime(review.getCreatedDateTime())
                .lastModifiedDateTime(review.getLastModifiedDateTime())
                .build();
    }
}
