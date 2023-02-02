package com.cakestation.backend.review.repository;

import com.cakestation.backend.review.domain.ReviewImage;
import com.cakestation.backend.review.domain.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    @Query("select rt.id from ReviewTag rt where rt.review.id =:reviewId")
    List<Long> findAllIdByReviewId(@Param("reviewId") Long reviewId);

    @Modifying(clearAutomatically = true)
    @Query("delete from ReviewTag rt where rt.id in :ids")
    void deleteReviewTagByIds(@Param("ids") List<Long> ids);
}
