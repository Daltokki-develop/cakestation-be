package com.cakestation.backend.store.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CakeStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cakestore_id")
    private Long id;

    private String name;

    private String address;

    private String businessHours;

    private String phoneNumber;

    private String thumbnail;

    private String webpageUrl;

    private String mapUrl;

    private String nearByStation;

}
