package com.cakestation.backend.review.repository;

import com.cakestation.backend.review.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    @Query("select ri.id from ReviewImage ri where ri.review.id =:reviewId")
    List<Long> findAllIdByReviewId(@Param("reviewId") Long reviewId);

    @Modifying(clearAutomatically = true)
    @Query("delete from ReviewImage ri where ri.id in :ids")
    void deleteReviewImageByIds(@Param("ids") List<Long> ids);
}
