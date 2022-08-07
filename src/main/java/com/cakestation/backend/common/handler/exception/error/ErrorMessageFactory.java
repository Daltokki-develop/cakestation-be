package com.cakestation.backend.common.handler.exception.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorMessageFactory {

    public static ErrorMessage from(HttpStatus httpStatus, String message) {
        return ErrorMessage.builder()
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(message)
                .build();
    }
}
