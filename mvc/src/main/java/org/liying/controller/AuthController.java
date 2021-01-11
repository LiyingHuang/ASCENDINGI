package org.liying.controller;

import org.liying.model.Role;
import org.liying.model.User;
import org.liying.service.JWTService;
import org.liying.service.RoleService;
import org.liying.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

// 1. validate user exist in db, and verify the password
// 2. generate JWToken -> then return Token
@RestController
@RequestMapping(value = {"/auth"})
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String errorMsg = "The email or password is not correct";
    private String tokenKeyWord = "Authorization";
    private String tokenType = "Bearer";
    @Autowired private JWTService jwtService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;


    // sign up (share the URI path "/auth")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    //public User createUser (@RequestBody User user){
    public ResponseEntity createUser (@RequestBody User user){
        try {
            if (userService.getUserByEmail(user.getEmail()) != null) {
                logger.error("This Email already exists! ");
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body("Email already registered! ");
            }
            if (userService.getUserByName(user.getName()) != null) {
                // logger for client
                // or ResponseEntity for client
                logger.error("This User Name already exists! ");
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body("Name already registered! ");
            }
            logger.debug("User:" + user.toString());
            User u = userService.save(user);
            Role role = roleService.getById(3l);
            u.addRole(role);
            userService.save(u);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body("Sign Up Successfully! ");
    }

    // login {"token": "<token>"}
    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity<Map> authentication(@RequestBody User user){
        //logger.debug("Im in AuthController...");
        Map<String, String> result = new HashMap<>();
        logger.debug("Username is: " + user.getEmail() +", password is: "+ user.getPassword());
        try{
            // get user from db through email and password
            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
            // if we can't get user from db -> errorMsg and ResponseEntity401
            if(u == null){
                result.put("msg", errorMsg);
                return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(result);
            }
            // else we get user ->  generateToken / ResponseEntity200
            String token = jwtService.generateToken(u);
            result.put("token", token);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg == null){msg = "BAD REQUEST!";}
            logger.error(msg);
            result.put("msg", msg);
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(result);
    }


/*
    // Version1: authentication

    // @RequestParam这种写法不好
    //private String authentication(@RequestParam(name = "username") String username,
    //                              @RequestParam(name = "password") String password){

    @RequestMapping(value = "", method = RequestMethod.POST)
    private String authentication(@RequestBody User user){
        logger.debug("Im in AuthController...");
        logger.debug("Username is: " + user.getEmail() +", password is: "+ user.getPassword());
        try{
            // 拿到user， 取user的name和password去和DB的对比，一样才给Token
            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
            String token = jwtService.generateToken(u);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/

}
