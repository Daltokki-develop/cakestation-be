package com.cakestation.backend.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class FileUploadFailedException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final String message = "파일을 업로드 할 수 없습니다.";
}
