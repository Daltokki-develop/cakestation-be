package com.cakestation.backend.review.service.dto;

import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Distance;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.Tag;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReviewDto {
    private Long storeId;
    private List<String> reviewImages;
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();
    private int cakeNumber;
    private String sheetType;
    private String requestOption;
    private DesignSatisfaction designSatisfaction;
    private int score;
    private List<Tag> tags;
    private String content;
}
