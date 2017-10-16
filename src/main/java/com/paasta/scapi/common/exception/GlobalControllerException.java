package com.paasta.scapi.common.exception;

/**
 * Created by ijlee on 2017-07-12.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException e){
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){return e.getMessage();}


}