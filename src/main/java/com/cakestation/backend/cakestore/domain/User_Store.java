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
@Table(name = "LIKE")
public class User_Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cake_store_id")
    private CakeStore cakeStore;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public static User_Store createLikeStore(User user, CakeStore store){
        return User_Store.builder()
                .user(user)
                .cakeStore(store)
                .build();
    }
}
