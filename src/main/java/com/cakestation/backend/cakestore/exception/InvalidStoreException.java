package com.cakestation.backend.cakestore.exception;

import com.cakestation.backend.common.exception.BadRequestException;
import com.cakestation.backend.common.exception.ErrorType;

public class InvalidStoreException extends BadRequestException {
    public InvalidStoreException(ErrorType errorType) {
        super(errorType);
    }
}
