package com.cakestation.backend.common.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    NOT_AUTHENTICATED(1001, "인증되지 않았습니다."),
    ALREADY_AUTHENTICATED(1002, "이미 인증정보가 존재합니다."),
    INVALID_TOKEN(1003, "유효하지 않은 토큰입니다."),
    FORBIDDEN(1004, "권한이 없습니다."),
    NOT_FOUND_USER(2001, "존재하지 않는 회원입니다."),
    NOT_FOUND_STORE(3001, "존재하지 않는 가게입니다."),
    CAN_NOT_PRESS_LIKE(3002, "이미 좋아요가 등록된 가게입니다."),
    NOT_FOUND_REVIEW(4001, "존재하지 않는 리뷰입니다."),
    CAN_NOT_UPLOAD_IMAGE(4002, "리뷰 이미지를 업로드할 수 없습니다.");

    private final int code;
    private final String message;

    ErrorType(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
