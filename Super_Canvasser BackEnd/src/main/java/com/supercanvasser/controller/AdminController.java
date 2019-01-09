package com.supercanvasser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.supercanvasser.bean.ResponseBean;
import com.supercanvasser.bean.Role;
import com.supercanvasser.bean.User;
import com.supercanvasser.bean.UserRole;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.exception.UsernameExistedException;
import com.supercanvasser.service.AdministratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.supercanvasser.constant.Constants.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController{


    @Autowired
    AdministratorService adminService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/PostUser")
    public ResponseBean<User> signUp(@RequestBody Map<String, Object> json) {

        String firstName = String.valueOf(json.get("firstName"));
        String lastName = String.valueOf(json.get("lastName"));
        String userName = String.valueOf(json.get("userName"));
        String password = String.valueOf(json.get("password"));
        List<String> roles =(ArrayList) json.get("role");
        if(userName == null || password == null){
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, Constants.CANNOT_EMPTY);
        }
        User existedUser = userService.getUserByUsername(userName);
        if(existedUser == null){
            User newUser = new User(firstName, lastName, userName, password);
            userService.addUser(newUser);
            userService.addUserRole(newUser.getId(), roles);
            logger.info("User sign up: {}" , userName);
//            logger.info("Password: {}", password);
            return new ResponseBean<>(HttpStatus.OK, SUCCESS_MSG);
        }
        else{
            throw new UsernameExistedException("User exists!");
        }
    }

    @GetMapping("/GetUserList")
    public ResponseBean<ArrayNode> getUserList(){
        List<User> users = userService.getAll();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for(User user : users){
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String username = user.getUsername();
            List<String> roles = userService.getRoleByUserId(user.getId());

            ObjectNode node = objectMapper.createObjectNode();
            ArrayNode rolesArr = objectMapper.valueToTree(roles);
            node.put("UserID", user.getId());
            node.put("FirstName", user.getFirstName());
            node.put("LastName", user.getLastName());
            node.put("UserName", user.getUsername());
            node.putArray("Role").addAll(rolesArr);

            arrayNode.add(node);
        }
        logger.info("Getting all users");
        return new ResponseBean<>(HttpStatus.OK, "/admin/GetUserList", arrayNode);
    }

    @PostMapping("/EditUser")
    public ResponseBean updateUser(@RequestBody Map<String, Object> json){

        Integer userID = Integer.valueOf(String.valueOf(json.get("userID")));
        String firstName = String.valueOf(json.get("firstName"));
        String lastName = String.valueOf(json.get("lastName"));
        String userName = String.valueOf(json.get("userName"));
        String password = String.valueOf(json.get("password"));
        List<String> roles = (ArrayList) json.get("role");

        userService.updateUser(new User(userID, firstName, lastName, userName, password));
        userService.updateUserRole(userID, roles);
        logger.info("Updating user data");
        return new ResponseBean(HttpStatus.OK, "/admin/EditUser");
    }


    @PostMapping("/EditUserLoadWindow")
    public ResponseBean<ObjectNode> getUser(@RequestBody Map<String, String> idJson){
        Integer userId = Integer.valueOf(idJson.get("userId"));
        User user = userService.getUserById(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        data.put("UserID", userId);
        data.put("FirstName", user.getFirstName());
        data.put("LastName", user.getLastName());
        data.put("UserName", user.getUsername());
        data.put("Password", user.getPassword());
        ArrayNode roleArr = objectMapper.valueToTree(userService.getRoleByUserId(userId));
        data.putArray("Role").addAll(roleArr);
        logger.info("Getting the data of user {}", user.getUsername());
        return new ResponseBean<>(HttpStatus.OK, "/admin/EditUserLoadWindow", data);
    }


    @PostMapping("/DeleteUser")
    public ResponseBean deleteUser(@RequestBody Map<String, String> idJson){
        Integer userID = Integer.valueOf(idJson.get("userID"));
        userService.deleteUser(userID);
        logger.info("Deleting user with id {}", userID);
        return new ResponseBean(HttpStatus.OK, "/admin/DeleteUser");
    }


    @PostMapping("/EditMaxDuration")
    public ResponseBean updateDuration(@RequestBody Map<String, String> json){
        try{
            Double duration = Double.valueOf(json.get("maxDuration"));
            adminService.updateDuration(duration);
        }catch (Exception e){
            return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.INVALID_PARAMETER);
        }
        logger.info("Updating global variable duration");
        return new ResponseBean(HttpStatus.OK, "/admin/EditMaxDuration");
    }


    @PostMapping("/EditAveSpeed")
    public ResponseBean updateAveSpeed(@RequestBody Map<String, String> json){
        try {
            Double aveSpeed = Double.valueOf(json.get("aveSpeed"));
            adminService.updateAverageSpeed(aveSpeed);
        }catch (Exception e){
            return new ResponseBean(HttpStatus.BAD_REQUEST, Constants.INVALID_PARAMETER);
        }
        logger.info("Updating global variable average speed");
        return new ResponseBean(HttpStatus.OK, "/admin/EditAveSpeed");
    }


    @GetMapping("/GetAveSpeed")
    public ResponseBean<String> getAveSpeed(){
        Double aveSpeed = adminService.getAveSpeed();
        logger.info("Getting global variable average speed");
        return new ResponseBean<>(HttpStatus.OK, "/admin/GetAveSpeed", String.valueOf(aveSpeed));
    }


    @GetMapping("/GetMaxDuration")
    public ResponseBean<String> getDuration(){
        Double duration = adminService.getDuration();
        logger.info("Getting global variable duration");
        return new ResponseBean<>(HttpStatus.OK, "/admin/GetMaxDuration", String.valueOf(duration));
    }

}
