package com.cakestation.backend.store.domain;

import java.net.URI;
import java.time.LocalDateTime;

import com.cakestation.backend.store.domain.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @OneToOne(mappedBy = "menu")
    private Store store;

    private URI phothUrl;

    private float innerLength;

    private float outerLength;

    private LocalDateTime createdTime;
}
