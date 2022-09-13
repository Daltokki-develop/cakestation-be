package com.cakestation.backend.badge.domain;


import com.cakestation.backend.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@IdClass(BadgeUser.class)
public class Badge_User {

    @Id
    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
