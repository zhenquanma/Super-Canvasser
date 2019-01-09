package com.supercanvasser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.supercanvasser.bean.ResponseBean;
import com.supercanvasser.bean.Role;
import com.supercanvasser.bean.User;
import com.supercanvasser.constant.Constants;
import com.supercanvasser.security.JWTTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController extends BaseController{


    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private JWTTokenProvider jwtTokenProvider;


    /**
     * Get the current user data
     * @param request
     * @return
     */
    @GetMapping("/")
    public ResponseBean<User> getUser(HttpServletRequest request){
        String token = jwtTokenProvider.extractToken(request);
        Integer id = Integer.valueOf(jwtTokenProvider.getUserId(token));
        return new ResponseBean<>(HttpStatus.OK, Constants.SUCCESS_MSG, userService.getUserById(id));
    }


    /**
     * Get the specific user data with its id or username
     * If no parameter was provided, current user data will be returned
     * @param id
     * @param username
     * @param request
     * @return
     */
    @GetMapping("/info")
    public ResponseBean<User> getUser(@RequestParam(value = "id", required = false) Integer id,
                                      @RequestParam(value = "username", required = false) String username,
                                      HttpServletRequest request) {

        if(id == null && username == null){
            logger.info("No id or username");
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, Constants.NOT_FOUND_MSG);
        }
        else if(id != null && username == null){
            logger.info("id = {}", id);
            return new ResponseBean<>(HttpStatus.OK, Constants.SUCCESS_MSG, userService.getUserById(id));
        }
        else if(id == null && username != null){
            logger.info("username = {}", username);
            return new ResponseBean<>(HttpStatus.OK, Constants.SUCCESS_MSG, userService.getUserByUsername(username));
        }
        else{
            logger.info("id = {}, username = {}", id, username);
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, Constants.FAILURE_MSG);
        }
    }




    @GetMapping("/logout")
    public ResponseBean logout(){
        return new ResponseBean(HttpStatus.OK, Constants.SUCCESS_LOGOUT_MSG);
    }



//    @GetMapping("/authorityList")
//    public List<String> authorityList(){
//        List<String> authentication = getAuthentication();
//        return authentication;
//    }




    /********************
     * For testing
     * ******************
     */
//    @PostMapping("/hello")
//    public ResponseBean<String> hello(@RequestBody String dname){
//        return new ResponseBean<>(HttpStatus.OK, Constants.SUCCESS_MSG, dname);
//    }
//
//    @GetMapping("/time")
//    public ResponseBean<List<String>> time(){
//
//        return new ResponseBean<>(HttpStatus.OK, Constants.SUCCESS_MSG,userService.getFormattedFreeDay(2));
//    }
//
//    @PostMapping("/arrayTest")
//    public ResponseBean arrayTest(@RequestBody Map<String, Object> map){
//        Integer id = Integer.valueOf(String.valueOf(map.get("id")));
//        List list = (ArrayList) map.get("arr");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode data = objectMapper.createObjectNode();
//        data.put("id", id);
//        ArrayNode arr = objectMapper.createArrayNode();
//
//        for (int i=0; i<list.size(); i++){
//            Map<String, String> dataMap = (LinkedHashMap)list.get(i);
//            String nodeId = String.valueOf(dataMap.get("id"));
//            String content = String.valueOf(dataMap.get("content"));
//            ObjectNode arrData = objectMapper.createObjectNode();
//            arrData.put("id", nodeId);
//            arrData.put("content", content);
//            arr.add(arrData);
//        }
//
//        data.putArray("arr").addAll(arr);
//        return new ResponseBean(HttpStatus.OK, "/arrayTest", data);
//    }

}
