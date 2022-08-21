package com.cakestation.backend.review.dto.response;

import com.cakestation.backend.review.domain.Review;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewResponse {

    private String username;
    private int cakeNumber;
    private String sheetType;
    private String requestOption;
    private List<String> reviewImages;
    private String content;
    private LocalDate createdDate;

    public static ReviewResponse from(Review review){
        return ReviewResponse.builder()
                .username(review.getWriter().getUsername())
                .cakeNumber(review.getCakeNumber())
                .sheetType(review.getSheetType())
                .requestOption(review.getRequestOption())
                .reviewImages(review.getImageUrls())
                .content(review.getContent())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
