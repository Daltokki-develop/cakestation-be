package com.cakestation.backend.review.service.dto;

import com.cakestation.backend.review.domain.DesignSatisfaction;
import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.review.domain.Tag;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateReviewDto {

    private Long storeId;
    private List<String> reviewImages = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();
    private int cakeNumber;
    private String sheetType;
    private String requestOption;
    private DesignSatisfaction designSatisfaction;
    private int score;
    private List<Tag> tags;
    private String content;

    @Builder
    public UpdateReviewDto(Long storeId, List<String> reviewImages, int cakeNumber, String sheetType, String requestOption, DesignSatisfaction designSatisfaction, int score, List<Tag> tags, String content) {
        this.storeId = storeId;
        this.reviewImages = reviewImages;
        this.imageUrls = new ArrayList<>();
        this.cakeNumber = cakeNumber;
        this.sheetType = sheetType;
        this.requestOption = requestOption;
        this.designSatisfaction = designSatisfaction;
        this.score = score;
        this.tags = tags;
        this.content = content;
    }
}

