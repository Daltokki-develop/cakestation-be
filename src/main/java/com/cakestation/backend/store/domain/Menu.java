package com.cakestation.backend.store.domain;

import java.net.URI;
import java.sql.Timestamp;

import com.cakestation.backend.common.BaseEntity;
import com.cakestation.backend.store.domain.Store;
import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @OneToOne(mappedBy = "menu")
    private Store store;

    private URI phothUrl;

    private float innerLength;

    private float outerLength;
}
