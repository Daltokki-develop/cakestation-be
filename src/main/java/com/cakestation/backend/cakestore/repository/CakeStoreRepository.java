package com.cakestation.backend.cakestore.repository;

import java.util.List;

import com.cakestation.backend.cakestore.domain.CakeStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeStoreRepository extends JpaRepository<CakeStore, Long> {

    List<CakeStore> findAllByNameContains(@Param("storeName") String StoreName);

    List<CakeStore> findAllByNearByStationContains(@Param("stationName") String stationName);

}
