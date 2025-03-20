package com.altev.ecommerce.exception.handler;

import com.altev.ecommerce.dto.CustomError;
import com.altev.ecommerce.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CustomError> handleProductNotFoundException(ProductNotFoundException e) {
        CustomError error = new CustomError(e.getMessage(), 1);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomError> handleProductRuntimeException(RuntimeException e) {
        CustomError error = new CustomError(e.getMessage(), 10);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
