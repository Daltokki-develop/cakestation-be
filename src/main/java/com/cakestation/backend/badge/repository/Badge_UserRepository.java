package com.cakestation.backend.badge.repository;

import com.cakestation.backend.badge.domain.Badge;
import com.cakestation.backend.badge.domain.Badge_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Badge_UserRepository extends JpaRepository<Badge_User, Long> {
}


