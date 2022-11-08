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

//    @Query(value = "select S.address as address , S.business_hours as businessHours ," +
//            " S.kakao_map_url as KakaoMapUrl , S.name as Storename , S.phone as phoneNumber , " +
//            "S.store_id as storeId , S.webpage_url as webpageUrl  " +
//            "from user_store as US " +
//            "inner join cake_store as S " +
//            "on S.store_id = US.store_id " +
//            "where US.user_id = :#{#User.id}", nativeQuery = true)
//    List<LikeStoreResponseInterface> findAllLikedStore(
//            @Param("User") User user
//    );

}