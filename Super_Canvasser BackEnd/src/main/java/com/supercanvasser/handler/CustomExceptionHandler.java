package com.supercanvasser.handler;

import com.supercanvasser.bean.ResponseBean;
import com.supercanvasser.exception.LocationException;
import com.supercanvasser.exception.TokenException;
import com.supercanvasser.exception.UsernameExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zhenquan Ma
 * @date   11/12/2018
 */
@RestControllerAdvice("com.supercanvasser.controller")
public class CustomExceptionHandler {

    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }


    @ExceptionHandler(Exception.class)
    public ResponseBean handleException(HttpServletRequest request, Throwable ex){
        HttpStatus status = getStatus(request);
        ex.printStackTrace();
        return new ResponseBean(status, ex.getMessage());
    }

}
