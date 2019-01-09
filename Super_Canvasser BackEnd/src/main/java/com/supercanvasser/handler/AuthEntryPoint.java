package com.supercanvasser.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercanvasser.bean.ResponseBean;
import com.supercanvasser.constant.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(new ResponseBean<>(HttpStatus.UNAUTHORIZED, Constants.ASK_FOR_LOGIN_MSG, null)));
    }
}
