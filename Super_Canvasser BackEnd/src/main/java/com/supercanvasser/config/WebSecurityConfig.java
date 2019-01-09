package com.supercanvasser.config;

import com.supercanvasser.constant.Constants;
import com.supercanvasser.handler.AuthEntryPoint;
import com.supercanvasser.handler.CustomExceptionHandler;
import com.supercanvasser.security.JWTAuthenticationFilter;
import com.supercanvasser.security.JWTLoginFilter;
import com.supercanvasser.security.JWTAuthenticationProvider;
import com.supercanvasser.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * SpringSecurity configuration
 * Binding JWTLoginFilter and JWTAuthenticationFilter together
 */
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * Put API in whitelist here
     * "login" API is default by framework, no need to add here
     */
    private static final String[] AUTH_WHITELIST = {
            // -- register url
            "/user/signup",
            "/login",
//            "/**"
    };

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private CustomExceptionHandler exceptionHandler;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                //No session needed
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Entry points
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                // Disallow everything else
                .antMatchers("/admin/**").hasRole(Constants.SYSTEM_ADMINISTRATOR)
                .antMatchers("/manager/**").hasRole(Constants.CAMPAIGN_MANAGER)
                .antMatchers("/canvasser/**").hasRole(Constants.CANVASSER)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)//.accessDeniedPage("/login")
                .and()
                // Default logout function
                .logout().permitAll();

        http.addFilterBefore(new JWTLoginFilter(authenticationManager(), jwtTokenProvider,exceptionHandler), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), jwtTokenProvider, exceptionHandler), BasicAuthenticationFilter.class);
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Use custom authentication provider
        auth.authenticationProvider(new JWTAuthenticationProvider(userDetailsService, bCryptPasswordEncoder));
    }

}
