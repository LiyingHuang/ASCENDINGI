package org.liying.controller;

import org.liying.model.Role;
import org.liying.model.User;
import org.liying.service.RoleService;
import org.liying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @GetMapping(value ="/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/roles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
}
