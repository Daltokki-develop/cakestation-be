package com.cakestation.backend.store.domain;

import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User_Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKED_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public static User_Store createLikeStroe(User user, Store store){
        return User_Store.builder()
                .user(user)
                .store(store)
                .build();
    }
}
