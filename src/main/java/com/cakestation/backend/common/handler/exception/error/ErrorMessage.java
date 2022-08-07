package com.cakestation.backend.common.handler.exception.error;

import lombok.*;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ErrorMessage {
    @Builder.Default
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private int status;
    private String error;
    private String message = "";
}
