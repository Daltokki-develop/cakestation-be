package com.cakestation.backend.common.dto;

import com.cakestation.backend.common.exception.ErrorType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ApiResponse<T> {
    private int code;
    private String responseMsg;
    private T result;

    public ApiResponse(int code, T result) {
        this.code = code;
        this.responseMsg = ErrorType.SUCCESS.getMessage();
        this.result = result;
    }
}

