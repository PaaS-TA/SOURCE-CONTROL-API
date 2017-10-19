package com.paasta.scapi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lee in jeong
 * @version 1.0 , 2017.7.7
 * @Description Login RuntimeException 관련 CutomException 처리
 */

@ControllerAdvice
public class CustomUserException extends Exception {

    public CustomUserException() {
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity CustomUserException(RuntimeException exception) {
        Map map = new HashMap();
        if("401 Unauthorized".equals(exception.getMessage())){
            map.put("error", "로그인에 실패하였습니다.");
            return new ResponseEntity(map, HttpStatus.UNAUTHORIZED);
        }else{
            map.put("error", exception.getMessage());
            return new ResponseEntity(exception.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



