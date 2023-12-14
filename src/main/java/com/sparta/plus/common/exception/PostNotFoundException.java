package com.sparta.plus.common.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message) {
        super(message);
    }
}

/* 사용법
throw new PostNotFoundException(
    messageSource.getMessage(
        "not.found.post",
        null,
        "Not Found Post",
        Locale.getDefault()
    ) 
)
 */
