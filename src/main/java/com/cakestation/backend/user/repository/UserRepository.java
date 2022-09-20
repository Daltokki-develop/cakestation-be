package com.cakestation.backend.user.repository;

import com.cakestation.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmail(@Param("email") String email);

    Optional<User> findByNickname(@Param("nickname") String nickname);

    @Modifying
    @Transactional
    @Query("update User u set u.nickname = :#{#newNickname} "
            + "where u.email = :#{#User.email}")
    public int updateNickname(
             @Param("User") User user,
             @Param("newNickname") String nickname
    );
}
