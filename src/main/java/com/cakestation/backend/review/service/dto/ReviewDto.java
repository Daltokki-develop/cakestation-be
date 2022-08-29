package com.cakestation.backend.review.service.dto;

import com.cakestation.backend.review.domain.Review;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private String username;
    private int cakeNumber;
    private String sheetType;
    private String requestOption;
    private List<String> reviewImages;
    private String content;
    private LocalDate createdDate;

    public static ReviewDto from(Review review){
        return ReviewDto.builder()
                .username(review.getWriter().getUsername())
                .cakeNumber(review.getCakeNumber())
                .sheetType(review.getSheetType())
                .requestOption(review.getRequestOption())
                .reviewImages(review.getImageUrls())
                .content(review.getContent())
                .build();
    }
}
