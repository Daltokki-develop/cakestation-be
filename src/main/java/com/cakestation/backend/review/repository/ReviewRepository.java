package com.cakestation.backend.review.repository;

import com.cakestation.backend.review.domain.Review;
import com.cakestation.backend.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from Review r where r.id in :ids")
    void deleteReviewByIds(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true)
    @Query("delete from ReviewImage ri where ri.review.id in :ids")
    void deleteReviewImagesByReviewIds(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true)
    @Query("delete from ReviewTag rt where rt.review.id in :ids")
    void deleteReviewTagsByReviewIds(@Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {"writer"})
    List<Review> findAllByWriterId(Long writerId, Pageable pageable);

    @EntityGraph(attributePaths = {"writer"})
    List<Review> findAllByCakeStoreId(Long storeId, Pageable pageable);

    @Query("select avg (r.score) from Review r where r.cakeStore.id =:storeId")
    Optional<Double> findAverageByStore(@Param("storeId") Long storeId);

    @EntityGraph(attributePaths = {"writer"})
    @Query("select r from Review r where r.writer.id =:writerId")
    List<Review> findAllByWriter(@Param("writerId") Long writerId);

}
