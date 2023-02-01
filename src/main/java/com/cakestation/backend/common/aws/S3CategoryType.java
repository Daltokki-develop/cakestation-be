package com.cakestation.backend.common.aws;

import lombok.Getter;

@Getter
public enum S3CategoryType {
    REVIEW("review");

    private final String category;

    S3CategoryType(String category) {
        this.category = category;
    }
}
