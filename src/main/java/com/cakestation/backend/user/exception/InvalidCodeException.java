package com.cakestation.backend.user.exception;

import com.cakestation.backend.common.exception.BadRequestException;
import com.cakestation.backend.common.exception.ErrorType;

public class InvalidCodeException extends BadRequestException {
    public InvalidCodeException(ErrorType errorType) {
        super(errorType);
    }
}
