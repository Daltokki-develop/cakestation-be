package com.cakestation.backend.repository;

import com.cakestation.backend.domain.Subway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubwayRepository extends JpaRepository<Subway,Long> {

}
