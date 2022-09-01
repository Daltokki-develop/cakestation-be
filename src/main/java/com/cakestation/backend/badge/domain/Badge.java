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

    //조인테이블을 통해 유저와 정보를 저장한다.
    @ManyToMany
    @JoinTable(name = "Badge_User",//조인 테이블 명
            joinColumns = @JoinColumn(name="badge_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userList = new ArrayList<>();

    public static Badge createBadge(Badge badge){
        return Badge.builder()
                .badgename(badge.getBadgename())
                .mission(badge.getMission())
                .build();
    }

    public void addUser(User user) {
        if(user != null) {
            userList.add(user);
        }
    }


}
