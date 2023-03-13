package com.cakestation.backend.cakestore.repository;

import com.cakestation.backend.cakestore.domain.CakeStore;
import com.cakestation.backend.cakestore.domain.LikeStore;
import com.cakestation.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeStoreRepository extends JpaRepository<LikeStore, Long> {
    Optional<LikeStore> findByUserAndCakeStore(User user, CakeStore cakeStore);

    @Query("select ls from LikeStore ls where ls.user.id =:userId")
    List<LikeStore> findAllByUser(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from LikeStore ls where ls.id in :ids")
    void deleteLikeStoresByIds(@Param("ids") List<Long> ids);
}