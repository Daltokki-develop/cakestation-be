package com.cakestation.backend.cakestore.domain;

import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.user.domain.User;
import lombok.*;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User_Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private CakeStore cakeStore;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static User_Store createLikeStore(User user, CakeStore store){
        return User_Store.builder()
                .user(user)
                .cakeStore(store)
                .build();
    }
}