package org.liying.service;

import org.liying.model.User;
import org.liying.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User save(User user) {
        return user;
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

}
