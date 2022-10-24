package com.cakestation.backend.store.domain;

import com.cakestation.backend.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User_Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(targetEntity = CakeStore.class)
    @JoinColumn(name = "STUDY_ID")
    private CakeStore stores;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User users;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public static User_Store createLikeStroe(User user, CakeStore cakeStore){
        return User_Store.builder()
                .users(user)
                .stores(cakeStore)
                .build();
    }
}
