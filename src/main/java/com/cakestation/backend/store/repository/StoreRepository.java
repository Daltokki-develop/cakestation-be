package com.cakestation.backend.store.repository;

import java.util.List;

import com.cakestation.backend.store.domain.CakeStore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<CakeStore,Long> {

    List<CakeStore> findAllByNameContains(@Param("storeName") String StoreName, Pageable pageable);

}
