package com.paasta.scapi.common.exception;

/**
 * Created by ijlee on 2017-07-12.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sonia.scm.client.ScmClientException;

@ControllerAdvice
@RestController
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException e){
          e.printStackTrace();
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
            e.printStackTrace();
        return e.getMessage();}

    @ExceptionHandler(ScmClientException.class)
    public ResponseEntity handleException(ScmClientException e){
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(e.getStatusCode()));
    }


}