package com.cakestation.backend.subway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subway {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subway_id")
    private Long id;

    @Column(nullable = false)
    private String line;

    @Column(nullable = false)
    private String station;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;
}
