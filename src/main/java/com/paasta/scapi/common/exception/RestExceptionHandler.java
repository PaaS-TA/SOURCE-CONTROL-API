package com.paasta.scapi.common.exception;

import com.paasta.scapi.service.CommonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lena on 2017-06-16.
 */
public class RestExceptionHandler extends CommonService{

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RestException.class)
    public String handleRestClientException(RestException e) {
        return e.getMessage();
    }

}
