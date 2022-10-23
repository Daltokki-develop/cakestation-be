package com.cakestation.backend.store.repository;

import com.cakestation.backend.store.domain.User_Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_storeRepository extends JpaRepository<User_Store,Long> {
}