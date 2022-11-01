package com.cakestation.backend.store.repository;

import com.cakestation.backend.store.domain.Store;
import com.cakestation.backend.store.domain.User_Store;
import com.cakestation.backend.store.dto.response.LikestoreResponseDto;
import com.cakestation.backend.store.dto.response.LikestoreResponseInterface;
import com.cakestation.backend.store.dto.response.StoreResponse;
import com.cakestation.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface User_StoreRepository extends JpaRepository<User_Store,Long> {

    public List<User_Store> findByUser_idAndStore_id(Long user_id , Long store_id);

    @Query(value = "select S.address as address , S.business_hours as businessHours ," +
            " S.kakao_map_url as KakaoMapUrl , S.name as Storename , S.phone as phoneNumber , " +
            "S.store_id as storeId , S.webpage_url as webpageUrl  "+
            "from user_store as US " +
            "inner join store as S " +
            "on S.store_id = US.store_id " +
            "where US.user_id = :#{#User.id}" , nativeQuery = true)
    public List<LikestoreResponseInterface> findAllLikedStore(
            @Param("User") User user
    );

}