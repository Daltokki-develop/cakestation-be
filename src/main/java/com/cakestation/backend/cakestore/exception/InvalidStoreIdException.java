package com.cakestation.backend.cakestore.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class InvalidStoreIdException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final String message = "등록되지 않은 가게 ID 입니다.";
}

