package com.cakestation.backend.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseDto<T> {

    private int code;
    private Boolean success;
    private String responseMsg;
    private T result;
}
