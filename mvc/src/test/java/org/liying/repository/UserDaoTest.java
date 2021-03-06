package org.liying.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liying.ApplicationBootstrap;
import org.liying.model.Role;
import org.liying.model.User;
import org.liying.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationBootstrap.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserService userService;

    private User user1;
    private Role role1;
    private Role role2;
    private String username="user12_ALIN";

    @Before
    public void setUp(){
        user1 = new User();
        user1.setEmail("user12@gmail.com");
        user1.setFirstName("LIYING");
        user1.setLastName("Huang");
        user1.setName(username);
        user1.setPassword("1111");
        user1.setSecretKey("1222");
        user1 = userDao.save(user1);

        role1 = new Role();
        role1.setName("role1_manager");
        role1.setAllowedCreate(true);
        role1.setAllowedDelete(true);
        role1.setAllowedRead(true);
        role1.setAllowedResource("yes");
        role1.setAllowedUpdate(true);
        role1 = roleDao.save(role1);
        user1.addRole(role1);

        role2 = new Role();
        role2.setName("role2_employee");
        role2.setAllowedCreate(false);
        role2.setAllowedDelete(false);
        role2.setAllowedRead(true);
        role2.setAllowedResource("no");
        role2.setAllowedUpdate(false);
        role2 = roleDao.save(role2);
        user1.addRole(role2);

        user1 = userDao.save(user1);
    }
    @After
    public void tearDown(){
        roleDao.delete(role1);
        roleDao.delete(role2);
        userDao.delete(user1);

    }
    @Test
    public void getUserTest(){
        List<User> user = userDao.getAllUsers();
        int expectedNumberOfUsers = 4;
        assertEquals(expectedNumberOfUsers, user.size());
    }
    @Test
    public void getUserWithRole(){
        User user = userDao.getUserByName(username);
        assertEquals(user.getRoles123().size(),2);
    }
    @Test
    public void getUserByCredentialTest(){
        User user = userDao.getUserByName(username);
    }
}
