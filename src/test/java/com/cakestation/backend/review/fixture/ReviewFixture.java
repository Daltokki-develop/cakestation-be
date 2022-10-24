package com.cakestation.backend.review.fixture;

import com.cakestation.backend.review.domain.*;
import com.cakestation.backend.review.controller.dto.request.CreateReviewRequest;
import com.cakestation.backend.review.service.dto.CreateReviewDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.cakestation.backend.store.fixture.StoreFixture.storeEntity;
import static com.cakestation.backend.user.fixture.UserFixture.getUserEntity;

public class ReviewFixture {

    public static Long REVIEW_ID = 1L;

    public static List<MultipartFile> IMAGES = List.of();
    public static List<String> IMAGE_URLS = List.of("abc","def"); // 리뷰 사진 url

    public static int CAKE_NUMBER = 1; // 케이크 호수

    public static String SHEET_TYPE = "초코"; // 시트 종류

    public static String REQUEST_OPTION = ""; // 추가 옵션

    public static DesignSatisfaction SATISFACTION = DesignSatisfaction.NORMAL; // 만족도

    public static int SCORE = 5; // 별점

    public static List<Tag> TAGS = List.of(Tag.CHEAP);

    public static String CONTENT = ""; // 내용

    public ReviewFixture() throws IOException {
    }


    public static CreateReviewRequest getCreateReviewRequest() throws IOException {

        return CreateReviewRequest.builder()
                .reviewImages(IMAGES)
//                .reviewImages(List.of(new MockMultipartFile(
//                        "test.png",
//                        "test.png",
//                        "multipart/form-data",
//                        new FileInputStream("src/test/resources/image/test.png"))))
                .cakeNumber(CAKE_NUMBER)
                .sheetType(SHEET_TYPE)
                .requestOption(REQUEST_OPTION)
                .designSatisfaction(SATISFACTION)
                .score(SCORE)
                .tags(TAGS)
                .content(CONTENT)
                .build();
    }

    public static CreateReviewDto getCreateReviewDto(){
        return CreateReviewDto.builder()
                .reviewImages(IMAGES)
                .imageUrls(IMAGE_URLS)
                .cakeNumber(CAKE_NUMBER)
                .sheetType(SHEET_TYPE)
                .requestOption(REQUEST_OPTION)
                .designSatisfaction(SATISFACTION)
                .score(SCORE)
                .tags(TAGS)
                .content(CONTENT)
                .build();
    }

    public static Review reviewEntity(){

        ReviewTag reviewTag = ReviewTag.builder()
                .tag(Tag.CHEAP)
                .build();

        Review review = Review.builder()
                .id(REVIEW_ID)
                .imageUrls(IMAGE_URLS)
                .cakeNumber(CAKE_NUMBER)
                .sheetType(SHEET_TYPE)
                .requestOption(REQUEST_OPTION)
                .designSatisfaction(SATISFACTION)
                .score(SCORE)
                .content(CONTENT)
                .reviewTags(List.of(reviewTag))
                .writer(getUserEntity())
                .cakeStore(storeEntity())
                .build();

        reviewTag.setReview(review);

        return review;

    }

}
