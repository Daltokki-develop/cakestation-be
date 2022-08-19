package com.cakestation.backend.review.repository;

import com.cakestation.backend.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.writer.id =:writerId")
    Review findAllByWriter(@Param("writerId") Long writerId);
}
