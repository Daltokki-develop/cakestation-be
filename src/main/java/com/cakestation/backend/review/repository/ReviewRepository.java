package com.cakestation.backend.review.repository;

import com.cakestation.backend.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"writer"})
    @Query("select r from Review r where r.writer.id =:writerId")
    List<Review> findAllByWriter (@Param("writerId") Long writerId, Pageable pageable);

    @EntityGraph(attributePaths = {"writer"})
    @Query("select r from Review r where r.store.id =:storeId")
    List<Review> findAllByStore (@Param("storeId") Long storeId, Pageable pageable);

    @Query("select avg (r.score) from Review r where r.store.id =:storeId")
    Optional<Double> findAverageByStore(@Param("storeId") Long storeId);

}
