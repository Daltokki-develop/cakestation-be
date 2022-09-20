package com.cakestation.backend.common.handler.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class EmailNotFoundException extends RuntimeException{
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final String message;
}
