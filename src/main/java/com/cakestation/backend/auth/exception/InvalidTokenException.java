package com.cakestation.backend.auth.exception;

import com.cakestation.backend.common.exception.BadRequestException;
import com.cakestation.backend.common.exception.ErrorType;

public class InvalidTokenException extends BadRequestException {
    public InvalidTokenException(ErrorType errorType) {
        super(errorType);
    }
}
