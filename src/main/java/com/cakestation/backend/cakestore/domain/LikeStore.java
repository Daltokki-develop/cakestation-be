package com.cakestation.backend.cakestore.domain;

import com.cakestation.backend.common.domain.BaseEntity;
import com.cakestation.backend.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "like_store")
public class LikeStore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_store_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cake_store_id")
    private CakeStore cakeStore;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public LikeStore(CakeStore cakeStore, User user) {
        this.id = null;
        this.cakeStore = cakeStore;
        this.user = user;
    }

    public static LikeStore createLikeStore(User user, CakeStore store) {
        return LikeStore.builder()
                .user(user)
                .cakeStore(store)
                .build();
    }
}
