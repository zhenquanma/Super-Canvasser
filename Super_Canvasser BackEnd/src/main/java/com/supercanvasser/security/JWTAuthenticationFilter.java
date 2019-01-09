package com.supercanvasser.security;

import com.supercanvasser.constant.Constants;
import com.supercanvasser.exception.TokenException;

import com.supercanvasser.handler.CustomExceptionHandler;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Retrieve token from header,
 * then validate token
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private JWTTokenProvider jwtTokenProvider;

    private CustomExceptionHandler exceptionHandler;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, CustomExceptionHandler exceptionHandler) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.extractToken(request);
        try{
            if(token != null && jwtTokenProvider.validateToken(token)){
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
            exceptionHandler.handleException(request, e);
        }
        filterChain.doFilter(request, response);
    }

}