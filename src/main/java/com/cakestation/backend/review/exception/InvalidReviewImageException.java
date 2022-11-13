package com.cakestation.backend.review.exception;

import com.cakestation.backend.common.exception.BadRequestException;
import com.cakestation.backend.common.exception.ErrorType;

public class InvalidReviewImageException extends BadRequestException {

    public InvalidReviewImageException(ErrorType errorType) {
        super(errorType);
    }
}
