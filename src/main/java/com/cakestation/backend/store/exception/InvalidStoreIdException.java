package com.cakestation.backend.store.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidStoreIdException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final String message = "등록되지 않은 가게 ID 입니다.";
}

