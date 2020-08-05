package org.liying.repository;

import org.liying.model.User;
import java.util.List;

public interface UserDao {
    User save(User user);
    User findById(Long Id);
    User getUserByEmail(String eamil);
    User getUserByName(String name);
    User getUserByCredentials(String emailOrName, String password) throws  Exception;
    boolean delete (User u);
    List<User> getAllUsers();
    User update(User u);
}
