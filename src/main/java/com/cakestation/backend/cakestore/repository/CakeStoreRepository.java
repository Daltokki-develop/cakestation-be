package com.cakestation.backend.cakestore.repository;

import java.util.List;
import java.util.Optional;

import com.cakestation.backend.cakestore.domain.CakeStore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface CakeStoreRepository extends JpaRepository<CakeStore, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CakeStore> findCakeStoreForUpdateById(Long cakeStoreId);

    List<CakeStore> findAllByNameContains(@Param("storeName") String StoreName, Pageable pageable);

    List<CakeStore> findAllByNearByStationContains(@Param("stationName") String stationName, Pageable pageable);

}
