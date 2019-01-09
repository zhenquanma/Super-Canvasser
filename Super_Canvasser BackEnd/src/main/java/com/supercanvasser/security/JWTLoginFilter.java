package com.supercanvasser.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.supercanvasser.dto.AuthenticationBean;
import com.supercanvasser.bean.ResponseBean;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.handler.CustomExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * After the success authentication, create a token, and send back to client
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTTokenProvider jwtTokenProvider;

    private CustomExceptionHandler exceptionHandler;


    protected Logger log = LoggerFactory.getLogger(JWTLoginFilter.class);

    public JWTLoginFilter(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, CustomExceptionHandler exceptionHandler) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.exceptionHandler = exceptionHandler;
    }

    // Extract username and password, then authenticate user token
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AuthenticationBean user = new ObjectMapper()
                    .readValue(req.getInputStream(), AuthenticationBean.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // This method will be called after login succeed, and create its token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // Build the token
        try {
            Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

            List roleList = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                roleList.add(grantedAuthority.getAuthority());
            }
            String token = jwtTokenProvider.createToken(authResult.getName(), roleList);

            // Return the token to the header
//            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Authorization", "Bearer " + token);
            log.info("Login as user: {}", authResult.getPrincipal().toString());
            log.info("---------Authentication Succeed---------");
            PrintWriter out = response.getWriter();

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode data = objectMapper.createObjectNode();
            data.put(Constants.USER_ID, jwtTokenProvider.getUserId(token));
            data.put(Constants.USER_NAME, authResult.getPrincipal().toString());

            //Test use only!!!!!!!!!!!!!!
            data.put("token", "111111");

            ArrayNode authArrayNode = objectMapper.valueToTree(loadRoles(authResult));
            data.putArray(Constants.ROLE).addAll(authArrayNode);
            ResponseBean responseBean = new ResponseBean(HttpStatus.OK, Constants.SUCCESS_LOGIN_MSG, data);
            out.write(objectMapper.writeValueAsString(responseBean));
            out.flush();
            out.close();

        } catch (Exception e) {
           exceptionHandler.handleException(request, e);
        }

    }

    private List<String> loadRoles(Authentication auth){

        List<String> authorities = new ArrayList<>();
        int i = auth.getAuthorities().size();
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String[] s =  authority.getAuthority().split("_");
            authorities.add(s[1]);
        }
        return authorities;
    }

}


