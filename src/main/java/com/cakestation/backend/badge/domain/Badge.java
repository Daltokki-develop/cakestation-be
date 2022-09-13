package com.cakestation.backend.badge.domain;

import com.cakestation.backend.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Badge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "badge_id")
    private Long id;

    //badge
    private String badgename;

    //클리어 해야하는 미션명을 작성<성공시 출력할 예정>
    private String mission;

    @OneToMany(mappedBy = "badge")
    private List<Badge_User> userList = new ArrayList<>();

    public static Badge createBadge(Badge badge){
        return Badge.builder()
                .badgename(badge.getBadgename())
                .mission(badge.getMission())
                .build();
    }

}
