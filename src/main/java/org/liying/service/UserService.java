package org.liying.service;

import org.liying.model.Role;
import org.liying.model.User;
import org.liying.repository.RoleDao;
import org.liying.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    public User save(User user) {
        return userDao.save(user);
    }
    public User findById(Long Id) {
        return userDao.findById(Id);
    }
    public User getUserByEmail(String eamil) {
        return userDao.getUserByEmail(eamil);
    }
    public boolean delete (User u){
        return userDao.delete(u);
    }
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }
    public User update(User u){
        return userDao.update(u);
    }
    public User getUserByName(String name){
        return userDao.getUserByName(name);
    }
    public User getUserByCredentials(String emailOrName, String password) throws Exception {return userDao.getUserByCredentials(emailOrName,password);}


    public User setRole(String userName, String roleName){
        User user = userDao.getUserByName(userName);
        Set<Role> roles = user.getRoles123(); // Get roles(Set) by user
        if (roleDao.getRoleByName(roleName) == null){
            System.out.println("Failed to get the role.");
        }
        roles.add(roleDao.getRoleByName(roleName)); // roles.add(role);
        user.setRoles123(roles); // roles is a Set Type
        userDao.update(user); // update user
        return user;
    }

    public User removeRole(String userName,String roleName){
        User user = userDao.getUserByName(userName);
        Set<Role> roles = user.getRoles123();
        if (roleDao.getRoleByName(roleName) == null){    // make sure the role is exist
            System.out.println("Failed to get the role.");
        }
        // List<Role> newRoles =roles.stream().filter(role -> !role.getName().contentEquals(roleName)).collect(Collectors.toList());
        // user.setRoles(newRoles);
        // ??
        roleDao.delete(roleDao.getRoleByName(roleName));
        userDao.update(user);
        return user;
    }



}
