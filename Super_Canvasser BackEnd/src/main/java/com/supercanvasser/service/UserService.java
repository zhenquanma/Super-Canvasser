package com.supercanvasser.service;

import com.supercanvasser.bean.Role;
import com.supercanvasser.bean.User;
import com.supercanvasser.bean.UserRole;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.mapper.RoleMapper;
import com.supercanvasser.mapper.UserMapper;
import com.supercanvasser.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAll() { return userMapper.getAll(); }

    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    /**
     * Get the roles of a user by its id
     *
     * @param userId
     * @return
     */
    public List<String> getRoleByUserId(Integer userId) {
        List<UserRole> userRoles = userRoleMapper.getByUserId(userId);
        List<String> roles = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roles.add(userRole.getRole().getName());
        }
        return roles;
    }


    /**
     * 1--->Campaign Manager
     * 2--->Canvasser
     * 3--->System Administrator
     */
    public List<User> getUsersByRoleId(Integer id) {
        List<UserRole> userRoles = userRoleMapper.getByRoleId(id);
        List<User> users = new ArrayList<>();
        for (UserRole userRole : userRoles
        ) {
            users.add(userRole.getUser());
        }
        return users;
    }

    public List<Integer> getUserIdsByRoleId(Integer id){
        List<User> users = getUsersByRoleId(id);
        List<Integer> userIds = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            userIds.add(users.get(i).getId());
        }
        return userIds;
    }



    public User addUser(User user) {
        //Encrypt the password for storing
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.addUser(user);
        return user;
    }

    public User updateUser(User user) {
        userMapper.updateUser(user.getId(), user.getFirstName(), user.getLastName());
//        userMapper.updateUser(user);
        return user;
    }


    public User updatePassword(User user, String password) {
        userMapper.updatePassword(user.getId(), password);
        return user;
    }


    public void deleteUser(Integer id) {
        userMapper.deleteUserById(id);
    }


    public void addFreeDay(Integer canvasserId, Date date){
        userMapper.addFreeDay(date, canvasserId);
    }


    public void addUserRole(UserRole userRole){
        userRoleMapper.addUserRole(userRole);
    }

    public void addUserRole(Integer userId, List<String> roles){
        //Add new role
        for(String role : roles) {
            UserRole userRole = new UserRole();
            if (role.equals(Constants.CAMPAIGN_MANAGER) && !userHasRole(userId,role)) {
                userRole.setUserId(userId);
                userRole.setRoleId(Constants.CAMPAIGN_MANAGER_ID);
                userRoleMapper.addUserRole(userRole);
            } else if (role.equals(Constants.CANVASSER) && !userHasRole(userId,role)) {
                userRole.setUserId(userId);
                userRole.setRoleId(Constants.CANVASSER_ID);
                userRoleMapper.addUserRole(userRole);
            } else if (role.equals(Constants.SYSTEM_ADMINISTRATOR) && !userHasRole(userId,role)) {
                userRole.setUserId(userId);
                userRole.setRoleId(Constants.SYSTEM_ADMINISTRATOR_ID);
                userRoleMapper.addUserRole(userRole);
            }
        }
    }

    public void updateUserRole(Integer userId, List<String> roles){

        //Check if need deletion
        List<UserRole> userRoles = userRoleMapper.getByUserId(userId);
        List<Integer> userRolesToBeDeleted = new ArrayList<>();
        int i = 0, j = 0;
        for(; i < userRoles.size(); i++){
            UserRole existedUserRole = userRoles.get(i);
            for(; j < roles.size(); j++){
                if(existedUserRole.getRole().getName().equals(roles.get(j))){
                    break;
                }
            }
            if(j == roles.size()){
                userRolesToBeDeleted.add(existedUserRole.getId());
            }
        }
        //Delete role
        if(userRolesToBeDeleted.size() > 0){
            for(Integer userRoleId : userRolesToBeDeleted){
                userRoleMapper.deleteUserRoleById(userRoleId);
            }
        }

        addUserRole(userId, roles);
    }

    private boolean userHasRole(Integer userId, String role){
        List<UserRole> userRoles = userRoleMapper.getByUserId(userId);
        for(UserRole userRole : userRoles){
            if (userRole.getRole().getName().equals(role)){
                return true;
            }
        }
        return false;
    }

    /**
     * Before add or update user role, need role format checking
     * @param roles
     * @return
     */
    public boolean verifyRoles(List<String> roles){
        boolean pass = true;
        UserRole userRole = new UserRole();
        for(String role : roles){
            if(!role.equals(Constants.CAMPAIGN_MANAGER) &&
                    !role.equals(Constants.CANVASSER) &&
                    !role.equals(Constants.SYSTEM_ADMINISTRATOR)){
                pass = false;
            }
        }
        return pass;
    }

    /**
     * Get free dates of a canvasser and formats each e.g. Wed Nov 21 2018
     * @param canvasserId
     * @return
     */
    public List<String> getFormattedFreeDay(Integer canvasserId){
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
        List<Date> dates = userMapper.getFreeDaysByCanvasserId(canvasserId);
        List<String> formattedDates = new ArrayList<>();
        for(int i = 0; i < dates.size(); i++){
            formattedDates.add(format.format(dates.get(i)));
        }
        return formattedDates;
    }

    public List<User> retrieveUsers(List<String> users){
        List<User> returnUsers = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            returnUsers.add(getUserByUsername(users.get(i)));
        }
        return returnUsers;
    }

    public List<String> retrieveUsersString(List<User> users){
        List<String> returnUsers = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            Integer userId = users.get(i).getId();
            String userName = userMapper.getUsernameById(userId);
            returnUsers.add(userName);
        }
        return returnUsers;
    }



    public void deleteFreeDay(Integer canvasserId, Date date){
        userMapper.deleteFreeDayByDateUserId(date, canvasserId);
    }

}
