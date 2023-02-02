package com.cakestation.backend.review.exception;

import com.cakestation.backend.common.exception.BadRequestException;
import com.cakestation.backend.common.exception.ErrorType;

public class InvalidReviewException extends BadRequestException {
    public InvalidReviewException(ErrorType errorType) {
        super(errorType);
    }
}
