package com.cakestation.backend.common.handler.error;

import lombok.*;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorMessage {
    @Builder.Default
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private int status;
    private String error;
    private String message;
}
