package org.liying.repository;

import org.liying.model.User;

import java.util.List;


public interface UserDao {
    User save(User user);
    User findById(Long Id);
    User getUserByEmail(String eamil);
    User getUserByCredentials(String email, String password);
    boolean delete (User u);
    List<User> getAllUsers();
}
