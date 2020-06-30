package org.liying.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liying.ApplicationBootstrap;
import org.liying.model.Role;
import org.liying.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationBootstrap.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    private User user1;
    private Role role1;
    private Role role2;

    @Before
    public void setUp(){
        user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setFirstName("LIYING");
        user1.setLastName("Huang");
        user1.setName("ALIN");
        user1.setPassword("1111");
        user1.setSecretKey("1222");
        user1 = userDao.save(user1);

        role1 = new Role();
        role1.setName("manager");
        role1.setAllowedCreate(true);
        role1.setAllowedDelete(true);
        role1.setAllowedRead(true);
        role1.setAllowedResource("yes");
        role1.setAllowedUpdate(true);
        // role1.setUsers(user1);
        role1 = roleDao.save(role1);

        role2 = new Role();
        role2.setName("user111");
        role2.setAllowedCreate(false);
        role2.setAllowedDelete(false);
        role2.setAllowedRead(true);
        role2.setAllowedResource("no");
        role2.setAllowedUpdate(false);
        // role2.setUsers(user1);
        role2 = roleDao.save(role2);
    }
    @After
    public void tearDown(){
        userDao.delete(user1);
        roleDao.delete(role1);
        roleDao.delete(role2);
    }
    @Test
    public void getUserTest(){
        List<User> user = userDao.getAllUsers();
        int expectedNumberOfUsers = 4;
        Assert.assertEquals(expectedNumberOfUsers, user.size());
    }
}
