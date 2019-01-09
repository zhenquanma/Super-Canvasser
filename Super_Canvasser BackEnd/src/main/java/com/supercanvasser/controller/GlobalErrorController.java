package com.supercanvasser.controller;

import com.supercanvasser.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Global Error handler
 * @author Zhenquan Ma
 * @date   11/12/2018
 */
@RestController
public class GlobalErrorController implements ErrorController {

    private final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;


    @Override
    public String getErrorPath() {
        return PATH;
    }


    @RequestMapping(value = PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseBean handleError(HttpServletRequest request){
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(new ServletWebRequest(request), true);
        return new ResponseBean(HttpStatus.valueOf(Integer.valueOf(attributes.get("status").toString())),
                attributes.get("message").toString());
    }

}
