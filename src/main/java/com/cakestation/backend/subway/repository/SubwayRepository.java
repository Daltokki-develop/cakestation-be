package com.cakestation.backend.subway.repository;

import com.cakestation.backend.subway.domain.Subway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayRepository extends JpaRepository<Subway,Long> {

}
