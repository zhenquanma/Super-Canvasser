package com.supercanvasser.security;

import com.supercanvasser.bean.Role;
import com.supercanvasser.exception.TokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Component
public class JWTTokenProvider {

    private String secretKey = "CSE308_DO_U_WANT_TO_BUILD_A_SNOWMAN";

    private int validityInDays = 1;

    protected Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public String createToken(String username, List<Role> roles){

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userDetailsService.getIdOfUsername(username));
        claims.put("role", roles.stream().map(s ->
                new SimpleGrantedAuthority("ROLE_" + s.getName())
                ).filter(Objects::nonNull));

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, validityInDays);
        Date exp = calendar.getTime();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userId").toString();
    }

    public String extractToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }


    public boolean validateToken(String token){

        String username = null;
        try {
            username = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (username != null) {
                return true;
            }

        } catch (ExpiredJwtException e) {
            logger.error("Token expired: {} " + e);
            throw new TokenException("Token expired");
        } catch (UnsupportedJwtException e) {
            logger.error("Token format unsupported: {} " + e);
            throw new TokenException("Token format unsupported");
        } catch (MalformedJwtException e) {
            logger.error("Token formed exception: {} " + e);
            throw new TokenException("Token formed exception");
        } catch (SignatureException e) {
            logger.error("Signature failed: {} " + e);
            throw new TokenException("Signature failed");
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: {} " + e);
            throw new TokenException("Illegal argument");
        }
        return false;
    }

    public Authentication getAuthentication(String token){
        String username = getUsername(token);
        logger.info("Username is {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), "", userDetails.getAuthorities());
    }

}
