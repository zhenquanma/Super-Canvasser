package com.supercanvasser.security;

import com.supercanvasser.bean.User;
import com.supercanvasser.bean.UserRole;
import com.supercanvasser.mapper.UserMapper;
import com.supercanvasser.mapper.UserRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    protected Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username + " not found");
        }
        List<SimpleGrantedAuthority> roles = getAuthorities(user);
        return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);

    }

    /**
     * Load the role of the given {@link User} from database
     * @param user
     * @return
     */
    private List<SimpleGrantedAuthority> getAuthorities(User user){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        List<UserRole> userRoles = userRoleMapper.getByUserId(user.getId());
        if (userRoles != null){
            for(UserRole userRole : userRoles){
                String role = userRole.getRole().getName();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            logger.info("Role: {}", authorities.toString());
        }
        return authorities;
    }

    /**
     * Load the id of the given username from database
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public String getIdOfUsername(String username) throws UsernameNotFoundException{
        User user = userMapper.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username + " not found");
        }
        return String.valueOf(user.getId());
    }
}
