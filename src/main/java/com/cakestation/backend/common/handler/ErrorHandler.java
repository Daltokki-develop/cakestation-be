package com.cakestation.backend.common.handler;


import com.cakestation.backend.common.handler.error.ErrorMessage;
import com.cakestation.backend.common.handler.error.ErrorMessageFactory;
import com.cakestation.backend.common.handler.exception.IdNotFoundException;
import com.cakestation.backend.store.exception.InvalidStoreIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorMessage> invalidStoreIdExceptionHandler(InvalidStoreIdException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getMessage()));
    }
}
