package com.cakestation.backend.cakestore.exception;

import com.cakestation.backend.common.exception.BadRequestException;
import com.cakestation.backend.common.exception.ErrorType;

public class InvalidLikeStoreException extends BadRequestException {
    public InvalidLikeStoreException(ErrorType errorType) {
        super(errorType);
    }
}
