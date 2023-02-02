package com.cakestation.backend.user.exception;

import com.cakestation.backend.common.exception.BadRequestException;
import com.cakestation.backend.common.exception.ErrorType;

public class InvalidUserException extends BadRequestException {
    public InvalidUserException(ErrorType errorType) {
        super(errorType);
    }
}
