package com.sparta.plus.common.exception;

import com.sparta.plus.common.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        RestResponse restResponse = new RestResponse(e.getMessage(),
            HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<RestResponse> handlerNullPointerException(NullPointerException e) {
        RestResponse restResponse = new RestResponse(e.getMessage(),
            HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<RestResponse> handlerPostNotFoundException(PostNotFoundException e) {
        RestResponse restResponse = new RestResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }
}
