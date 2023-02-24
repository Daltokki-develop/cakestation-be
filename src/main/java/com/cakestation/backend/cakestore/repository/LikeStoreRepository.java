package com.cakestation.backend.cakestore.repository;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeStoreRepository extends JpaRepository<LikeStore, Long> {
    Optional<LikeStore> findByUserAndCakeStore(User user, CakeStore cakeStore);
    List<LikeStore> findLikeStoresByUser(User user);

}