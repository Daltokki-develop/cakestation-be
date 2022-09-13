package com.cakestation.backend.badge.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
//
@EqualsAndHashCode
public class BadgeUser implements Serializable {

    private Long badge;

    private Long user;

}
